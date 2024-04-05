package org.tetris;

import com.sun.source.doctree.TextTree;
import org.tetris.block.Block;
import org.tetris.block.BlockType;
import org.tetris.block.BoundingBox;
import org.tetris.block.Position;
import org.tetris.event.*;
import org.tetris.event.EventListener;
import org.tetris.event.Listener;
import org.tetris.screen.Painter;
import org.tetris.utils.Tuple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game implements Cloneable {
	List<BlockType> next;
	BlockType hold;
	Block currentBlock;
	List<Block> blocks;
	boolean isRunning, canHold;
	private List<Listener> listeners;
	private final Random random = ThreadLocalRandom.current();
	public Block getCurrentBlock() {
		return currentBlock;
	}
	public List<Block> getBlocks() {
		return blocks;
	}
	public List<BlockType> getNext() {
		return next;
	}
	public BlockType getHold() {
		return hold;
	}
	public Game() {
		listeners = new ArrayList<>();
		randomNextGenerate(4);
		currentBlock = nextBlock();
		blocks = new ArrayList<>();
		hold = null;
		canHold = true;
		isRunning = false;
		new Thread(new Runnable() {
			long delay = 0L;
			long delayR = 0L;
			long delayL = 0L;
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(30L);
						if (currentBlock.isSoftDropping()) {
							currentBlock.getPos().setY(currentBlock.getPos().getY() - 1);
							if (currentBlock.getPos().isCollision(Game.this, currentBlock.getBox())) {
								currentBlock.getPos().setY(currentBlock.getPos().getY() + 1);
							}
						} else {
							delay += 30L;
							if (delay >= 500) {
								currentBlock.getPos().setY(currentBlock.getPos().getY() - 1);
								if (currentBlock.getPos().isCollision(Game.this, currentBlock.getBox())) {
									currentBlock.getPos().setY(currentBlock.getPos().getY() + 1);
								}
								delay = 0L;
							}
						}
						if (currentBlock.isMoveToR()) {
							delayR += 30L;
							if (delayR >= 210) {
								currentBlock.getPos().setX(currentBlock.getPos().getX() + 1);
								if (currentBlock.getPos().isCollision(Game.this, currentBlock.getBox())) {
									currentBlock.getPos().setX(currentBlock.getPos().getX() - 1);
								}
							}
						} else {
							delayR = 0L;
						}
						if (currentBlock.isMoveToL()) {
							delayL += 30L;
							if (delayL >= 210) {
								currentBlock.getPos().setX(currentBlock.getPos().getX() - 1);
								if (currentBlock.getPos().isCollision(Game.this, currentBlock.getBox())) {
									currentBlock.getPos().setX(currentBlock.getPos().getX() + 1);
								}
							}
						} else {
							delayL = 0L;
						}
					} catch (InterruptedException ignored) {
					}
				}
			}
		}, "GameUpdateThread").start();
		new Thread(new Runnable() {
			Map<Tuple<Position, BoundingBox>, Long> drop = new HashMap<>();
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(50L);
						Tuple<Position, BoundingBox> tuple = new Tuple<>(currentBlock.getPos(), currentBlock.getBox());
						long d = drop.getOrDefault(tuple, 0L);
						if (d >= 500L && currentBlock.getPos().isLowPos(Tetris.getGame(), currentBlock.getBox())) {
							currentBlock.pin();
							drop = new HashMap<>();
						}
						drop.remove(tuple);
						drop.put(tuple, d + 50L);
					} catch (InterruptedException ignored) {
					}
				}
			}
		}).start();
	}
	public void holdBlock() {
		if (!canHold) return;
		BlockHoldEvent event = new BlockHoldEvent(getCurrentBlock(), getHold());
		callEvent(event);
		if (event.isCancelled()) return;
		if (hold == null) {
			hold = currentBlock.getType();
			currentBlock = nextBlock();
			currentBlock.remove();
			canHold = false;
			return;
		}
		BlockType temp = hold;
		hold = currentBlock.getType();
		currentBlock.remove();
		currentBlock = new Block(temp);
		canHold = false;
	}
	public Block nextBlock() {
		canHold = true;
		BlockType type = next.remove(0);
		randomNextGenerate();
		return new Block(type);
	}
	public void randomNextGenerate() {
		randomNextGenerate(1);
	}
	public void randomNextGenerate(int count) {
		if (next == null) next = new ArrayList<>();
		for (int ignored : new int[count]) {
			next.add(BlockType.getById(random.nextInt(7)));
		}
	}
	public void delLine(int y) {
		for (int x = -5; x < 5; x++) {
			Tuple<Block, Tuple<Integer, Integer>> b = getBlockAt(x, y);
			BoundingBox box = b.getA().getBox();
			box.removeLoc(b.getB());
			b.getA().setBox(box);
		}
		for (int delY = y + 1; delY < 20; delY++) {
			for (int x = -5; x < 5; x++) {
				if (hasBlockAt(x, delY)) {
					Tuple<Block, Tuple<Integer, Integer>> b = getBlockAt(x, delY);
					BoundingBox box = b.getA().getBox();
					Tuple<Integer, Integer> tuple = b.getB();
					tuple.setB(tuple.getB() - 1);
					box.addLoc(tuple);
					box.removeLoc(b.getB());
					b.getA().setBox(box);
				}
			}
		}
	}
	public Tuple<Block, Tuple<Integer, Integer>> getBlockAt(int x, int y) {
		for (Block block : blocks) {
			for (Tuple<Integer, Integer> tuple : block.getBox().getBox()) {
				if (block.getPos().getX() + tuple.getA() == x && block.getPos().getY() + tuple.getB() == y) return new Tuple<>(block, tuple);
			}
		}
		return null;
	}
	public boolean hasBlockAt(int x, int y) {
		for (Block block : blocks) {
			for (Tuple<Integer, Integer> tuple : block.getBox().getBox()) {
				if (block.getPos().getX() + tuple.getA() == x && block.getPos().getY() + tuple.getB() == y) return true;
			}
		}
		return false;
	}
	public void registerListener(Listener listener) {
		listeners.add(listener);
	}
	public void unregisterAll() {
		listeners = new ArrayList<>();
	}
	public void callEvent(Event e) {
		for (Listener listener : listeners) {
			List<Method> methods = Arrays.stream(listener.getClass().getDeclaredMethods()).filter(method -> method.getAnnotation(EventListener.class) != null).toList();
			for (Method method : methods) {
				if (method.getParameterCount() != 1) continue;
				if (method.getParameterTypes()[0].isInstance(e)) {
					if (e instanceof Cancellable c) if (c.isCancelled() && method.getAnnotation(EventListener.class).ignoreCancelled()) continue;
					try {
						method.invoke(listener, e);
					} catch (IllegalAccessException | InvocationTargetException ignored) {
					}
				}
			}
		}
	}
}

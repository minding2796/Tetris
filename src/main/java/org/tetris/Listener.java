package org.tetris;

import org.tetris.event.BlockPinEvent;
import org.tetris.event.EventListener;
import org.tetris.event.KeyPressEvent;
import org.tetris.event.KeyReleaseEvent;

import static org.tetris.KeyMap.*;

public class Listener implements org.tetris.event.Listener {
	@EventListener
	public void onPinBlock(BlockPinEvent e) {
		Game game = Tetris.getGame();
		game.blocks.add(e.getBlock());
		game.currentBlock = game.nextBlock();
		if (game.getCurrentBlock().getPos().isCollision(game, game.getCurrentBlock().getBox())) {
			Tetris.gameOver();
		}
		int lineCount = 0;
		for (int y = 20; y >= 0; y--) {
			boolean delete = true;
			for (int x = -5; x < 5; x++) {
				if (!game.hasBlockAt(x, y)) delete = false;
			}
			if (delete) {
				game.delLine(y);
				lineCount++;
			}
		}
		Tetris.addScore(Tetris.calcScore(lineCount));
	}
	@EventListener
	public void onKeyPress(KeyPressEvent e) {
		int keycode = e.getKeyEvent().getKeyCode();
		Game game = Tetris.getGame();
		KeyMap map = getByKeyCode(keycode);
		if (map == null) return;
		switch (map) {
			case ROTATE_D -> game.getCurrentBlock().spinD();
			case HOLD -> game.holdBlock();
			case ROTATE_R -> game.getCurrentBlock().spinR();
			case ROTATE_L -> game.getCurrentBlock().spinL();
			case HARD_DROP -> game.getCurrentBlock().hardDrop();
			
			case SOFT_DROP -> game.getCurrentBlock().softDrop();
			case LEFT -> game.getCurrentBlock().moveL();
			case RIGHT -> game.getCurrentBlock().moveR();
			default -> {}
		}
	}
	@EventListener
	public void onKeyRelease(KeyReleaseEvent e) {
		int keycode = e.getKeyEvent().getKeyCode();
		Game game = Tetris.getGame();
		KeyMap map = getByKeyCode(keycode);
		if (map == null) return;
		switch (map) {
			case SOFT_DROP -> game.getCurrentBlock().softDropEnd();
			case LEFT -> game.getCurrentBlock().moveLEnd();
			case RIGHT -> game.getCurrentBlock().moveREnd();
			default -> {}
		}
	}
}

package org.tetris.screen;

import org.tetris.Game;
import org.tetris.Tetris;
import org.tetris.block.Block;
import org.tetris.block.BlockType;
import org.tetris.block.BoundingBox;
import org.tetris.block.Position;
import org.tetris.utils.Tuple;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Painter {
	private final static int size = 30;
	private static final Consumer<Graphics> defaultGame = g -> {
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, 675, 700);
		g.setColor(new Color(255, 255, 255));
		for (int i = size * 6; i < size * 16; i += size) {
			for (int j = size; j <= size * 20; j += size) {
				g.drawRect(i, j, size, size);
			}
		}
		g.drawRect(size, size, size * 4, size * 4);
		g.drawRect(size * 17, size, size * 4, size * 4);
	};
	public static void updateScreen(Screen screen, Game game) {
		Graphics graphics = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB).getGraphics();
		if (game == null) {
			screen.drawer.paint(graphics, g -> {
				g.setColor(Color.white);
				g.fillRect(0, 0, 675, 700);
				g.setColor(Color.BLACK);
				g.drawString("GAME OVER", 300, 350);
				g.drawString("Score : " + Tetris.getScore(), 300, 370);
			});
			return;
		}
		screen.drawer.paint(graphics, g -> {
			defaultGame.accept(g);
			g.setColor(Color.white);
			g.drawString("Score : " + Tetris.getScore(), 30, 630);
			List<Block> blocks = new ArrayList<>(game.getBlocks());
			Block cBlock = game.getCurrentBlock();
			blocks.add(cBlock);
			List<BlockType> nextBlocks = new ArrayList<>(game.getNext());
			BlockType hold = game.getHold();
			if (hold != null) {
				BoundingBox holdBox = new BoundingBox(hold);
				for (Tuple<Integer, Integer> tuple : holdBox.getBox()) {
					int defX = size * 2;
					int defY = size * 3;
					g.setColor(hold.getColor());
					g.fillRect(defX + tuple.getA() * 30, defY - tuple.getB() * 30, size, size);
				}
			}
			if (!nextBlocks.isEmpty()){
				int repeat = 0;
				for (BlockType type : nextBlocks) {
					BoundingBox nextBox = new BoundingBox(type);
					for (Tuple<Integer, Integer> tuple : nextBox.getBox()) {
						int defX = size * 18;
						int defY = size * 3 + repeat * size * 5;
						g.setColor(type.getColor());
						g.fillRect(defX + tuple.getA() * 30, defY - tuple.getB() * 30, size, size);
					}
					repeat++;
				}
			}
			if (!blocks.isEmpty()){
				for (Block block : blocks) {
					BoundingBox blockBox = block.getBox();
					int defX0 = size * 11;
					int defY0 = size * 20;
					Position pos = block.getPos();
					int posX = defX0 + pos.getX() * size;
					int posY = defY0 - pos.getY() * size;
					for (Tuple<Integer, Integer> tuple : blockBox.getBox()) {
						g.setColor(block.getType().getColor());
						g.fillRect(posX + tuple.getA() * 30, posY - tuple.getB() * 30, size, size);
					}
				}
			}
			{
				BoundingBox blockBox = cBlock.getBox();
				int defX0 = size * 11;
				int defY0 = size * 20;
				Position pos = cBlock.getPos().getLow(game, cBlock.getBox());
				int posX = defX0 + pos.getX() * size;
				int posY = defY0 - pos.getY() * size;
				for (Tuple<Integer, Integer> tuple : blockBox.getBox()) {
					Color color = cBlock.getType().getColor().brighter();
					g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 75));
					g.fillRect(posX + tuple.getA() * 30, posY - tuple.getB() * 30, size, size);
				}
			}
		});
	}
}

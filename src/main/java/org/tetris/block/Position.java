package org.tetris.block;

import org.tetris.Game;
import org.tetris.Tetris;
import org.tetris.utils.Tuple;

import java.util.List;

public class Position {
	int x, y;
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Position(Tuple<Integer, Integer> tuple) {
		this.x = tuple.getA();
		this.y = tuple.getB();
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public static int top(BoundingBox box) {
		int top = -1;
		for (Tuple<Integer, Integer> tuple : box.getBox()) {
			if (top < tuple.getB()) top = tuple.getB();
		}
		top++;
		return 20 - top;
	}
	public boolean isCollision(Game game, BoundingBox box) {
		if (game == null) return true;
		{
			int top = -100;
			for (Tuple<Integer, Integer> tuple : box.getBox()) {
				if (top < tuple.getB()) top = tuple.getB();
			}
			top++;
			if (y + top > 20) return true;
		}
		{
			int low = 100;
			for (Tuple<Integer, Integer> tuple : box.getBox()) {
				if (low > tuple.getB()) low = tuple.getB();
			}
			if (y + low < 0) return true;
		}
		{
			int top = -100;
			for (Tuple<Integer, Integer> tuple : box.getBox()) {
				if (top < tuple.getA()) top = tuple.getA();
			}
			if (x + top > 4) return true;
		}
		{
			int low = 100;
			for (Tuple<Integer, Integer> tuple : box.getBox()) {
				if (low > tuple.getA()) low = tuple.getA();
			}
			if (x + low < -5) return true;
		}
		for (Block block : game.getBlocks()) {
			for (Tuple<Integer, Integer> tuple1 : box.getBox()) {
				for (Tuple<Integer, Integer> tuple2 : block.getBox().getBox()) {
					if (tuple1.getA() + getX() == tuple2.getA() + block.getPos().getX()) {
						if (tuple1.getB() + getY() == tuple2.getB() + block.getPos().getY()) return true;
					}
				}
			}
		}
		return false;
	}
	public Position getLow(Game game, BoundingBox box) {
		int temp = y;
		do y--; while(!isCollision(game, box));
		y++;
		Position position = new Position(x, y);
		y = temp;
		return position;
	}
	public boolean isLowPos(Game game, BoundingBox box) {
		return getLow(game, box).equals(this);
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Position position = (Position) o;
		return x == position.x && y == position.y;
	}
	
	@Override
	public int hashCode() {
		return ((Integer) x).hashCode() + ((Integer) y).hashCode();
	}
}

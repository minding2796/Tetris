package org.tetris.block;

import org.tetris.utils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BoundingBox {
	List<Tuple<Integer, Integer>> box;
	Rotate rot = Rotate.DEG_0;
	public BoundingBox(List<Tuple<Integer, Integer>> box) {
		this.box = box;
	}
	@SafeVarargs
	public BoundingBox(Tuple<Integer, Integer>... box) {
		this.box = Arrays.stream(box).toList();
	}
	public void addLoc(Tuple<Integer, Integer> loc) {
		box.add(loc);
	}
	public void removeLoc(Tuple<Integer, Integer> loc) {
		box.remove(loc);
	}
	public BoundingBox(BlockType type) {
		if (type == null) return;
		switch (type) {
			case I -> {
				List<Tuple<Integer, Integer>> list = new ArrayList<>();
				list.add(new Tuple<>(0, -1));
				list.add(new Tuple<>(0, 0));
				list.add(new Tuple<>(0, 1));
				list.add(new Tuple<>(0, 2));
				this.box = list;
			}
			case S -> {
				List<Tuple<Integer, Integer>> list = new ArrayList<>();
				list.add(new Tuple<>(-1, 0));
				list.add(new Tuple<>(0, 0));
				list.add(new Tuple<>(0, 1));
				list.add(new Tuple<>(1, 1));
				this.box = list;
			}
			case Z -> {
				List<Tuple<Integer, Integer>> list = new ArrayList<>();
				list.add(new Tuple<>(1, 0));
				list.add(new Tuple<>(0, 0));
				list.add(new Tuple<>(0, 1));
				list.add(new Tuple<>(-1, 1));
				this.box = list;
			}
			case J ->{
				List<Tuple<Integer, Integer>> list = new ArrayList<>();
				list.add(new Tuple<>(-1, 0));
				list.add(new Tuple<>(0, 0));
				list.add(new Tuple<>(0, 1));
				list.add(new Tuple<>(0, 2));
				this.box = list;
			}
			case L -> {
				List<Tuple<Integer, Integer>> list = new ArrayList<>();
				list.add(new Tuple<>(1, 0));
				list.add(new Tuple<>(0, 0));
				list.add(new Tuple<>(0, 1));
				list.add(new Tuple<>(0, 2));
				this.box = list;
			}
			case T -> {
				List<Tuple<Integer, Integer>> list = new ArrayList<>();
				list.add(new Tuple<>(-1, 0));
				list.add(new Tuple<>(0, 0));
				list.add(new Tuple<>(1, 0));
				list.add(new Tuple<>(0, 1));
				this.box = list;
			}
			case O -> {
				List<Tuple<Integer, Integer>> list = new ArrayList<>();
				list.add(new Tuple<>(1, 0));
				list.add(new Tuple<>(0, 0));
				list.add(new Tuple<>(1, 1));
				list.add(new Tuple<>(0, 1));
				this.box = list;
			}
			default -> {
				this.box = null;
			}
		}
	}
	public List<Tuple<Integer, Integer>> getBox() {
		return box;
	}
	public void updateRot(Rotate rotate) {
		int deg = rotate.getDeg() - rot.getDeg() < 0 ? rotate.getDeg() - rot.getDeg() + 360 : rotate.getDeg() - rot.getDeg();
		rot = rotate;
		switch (deg) {
			case 90 -> {
				List<Tuple<Integer, Integer>> newBox = new ArrayList<>();
				for (Tuple<Integer, Integer> tuple : box) {
					newBox.add(cycle(tuple));
				}
				box = newBox;
			}
			case 180 -> {
				List<Tuple<Integer, Integer>> newBox = new ArrayList<>();
				for (Tuple<Integer, Integer> tuple : box) {
					newBox.add(cycle(cycle(tuple)));
				}
				box = newBox;
			}
			case 270 -> {
				List<Tuple<Integer, Integer>> newBox = new ArrayList<>();
				for (Tuple<Integer, Integer> tuple : box) {
					newBox.add(cycle(cycle(cycle(tuple))));
				}
				box = newBox;
			}
			default -> {
			
			}
		}
	}
	private Tuple<Integer, Integer> cycle(Tuple<Integer, Integer> tuple) {
		int x;
		int y;
		if (tuple.getA() == -1 && tuple.getB() == -1) {
			x = -1;
			y = 2;
		} else if (tuple.getA() == -1 && tuple.getB() == 0) {
			x = 0;
			y = 2;
		} else if (tuple.getA() == -1 && tuple.getB() == 1) {
			x = 1;
			y = 2;
		} else if (tuple.getA() == -1 && tuple.getB() == 2) {
			x = 2;
			y = 2;
		} else if (tuple.getA() == 0 && tuple.getB() == 2) {
			x = 2;
			y = 1;
		} else if (tuple.getA() == 1 && tuple.getB() == 2) {
			x = 2;
			y = 0;
		} else if (tuple.getA() == 2 && tuple.getB() == 2) {
			x = 2;
			y = -1;
		} else if (tuple.getA() == 2 && tuple.getB() == 1) {
			x = 1;
			y = -1;
		} else if (tuple.getA() == 2 && tuple.getB() == 0) {
			x = 0;
			y = -1;
		} else if (tuple.getA() == 2 && tuple.getB() == -1) {
			x = -1;
			y = -1;
		} else if (tuple.getA() == 1 && tuple.getB() == -1) {
			x = -1;
			y = 0;
		} else if (tuple.getA() == 0 && tuple.getB() == -1) {
			x = -1;
			y = 1;
		} else if (tuple.getA() == 0 && tuple.getB() == 0) {
			x = 0;
			y = 1;
		} else if (tuple.getA() == 0 && tuple.getB() == 1) {
			x = 1;
			y = 1;
		} else if (tuple.getA() == 1 && tuple.getB() == 1) {
			x = 1;
			y = 0;
		} else if (tuple.getA() == 1 && tuple.getB() == 0) {
			x = 0;
			y = 0;
		} else {
			x = tuple.getA();
			y = tuple.getB();
		}
		return new Tuple<>(x, y);
	}
	@Override
	public int hashCode() {
		return Objects.hash(box, rot);
	}
}

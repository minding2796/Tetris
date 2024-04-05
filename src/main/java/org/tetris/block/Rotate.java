package org.tetris.block;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public enum Rotate {
	DEG_0(0),
	DEG_90(90),
	DEG_180(180),
	DEG_270(270);
	final int deg;
	Rotate(int deg) {
		this.deg = deg;
	}
	public Rotate getByDeg(int deg) {
		List<Rotate> rotate = Arrays.stream(values()).filter(r -> r.deg == deg).toList();
		if (rotate.size() != 1) return null;
		return rotate.get(0);
	}
	public int getDeg() {
		return deg;
	}
	public Rotate r() {
		return getByDeg(deg + 90 >= 360 ? deg + 90 - 360 : deg + 90);
	}
	public Rotate l() {
		return getByDeg(deg + 270 >= 360 ? deg + 270 - 360 : deg + 270);
	}
	public Rotate d() {
		return getByDeg(deg + 180 >= 360 ? deg + 180 - 360 : deg + 180);
	}
}

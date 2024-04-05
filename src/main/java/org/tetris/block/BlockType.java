package org.tetris.block;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public enum BlockType {
	I(0, Color.CYAN),
	S(1, Color.GREEN),
	Z(2, Color.RED),
	J(3, Color.BLUE),
	L(4, Color.ORANGE),
	T(5, new Color(141, 0, 197)),
	O(6, Color.YELLOW);
	final int id;
	final Color color;
	BlockType(int id, Color color) {
		this.id = id;
		this.color = color;
	}
	public static BlockType getById(int id) {
		List<BlockType> blockType = Arrays.stream(values()).filter(bT -> bT.id == id).toList();
		if (blockType.size() != 1) return null;
		return blockType.get(0);
	}
	public int getId() {
		return id;
	}
	public Color getColor() {
		return color;
	}
}

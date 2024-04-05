package org.tetris;

import java.util.Arrays;
import java.util.List;

public enum KeyMap {
	ROTATE_D(65),
	HOLD(67),
	HARD_DROP(32),
	SOFT_DROP(40),
	ROTATE_R(38),
	ROTATE_L(90),
	LEFT(37),
	RIGHT(39);
	final int keycode;
	KeyMap(int keycode) {
		this.keycode = keycode;
	}
	public static KeyMap getByKeyCode(int keycode) {
		List<KeyMap> key = Arrays.stream(values()).filter(keyC -> keyC.keycode == keycode).toList();
		if (key.size() != 1) return null;
		return key.get(0);
	}
	public int getKeyCode() {
		return keycode;
	}
}

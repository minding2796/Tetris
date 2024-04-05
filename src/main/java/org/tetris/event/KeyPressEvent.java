package org.tetris.event;

import java.awt.event.KeyEvent;

public class KeyPressEvent implements Event {
	private final KeyEvent keyEvent;
	public KeyPressEvent(KeyEvent keyEvent) {
		this.keyEvent = keyEvent;
	}
	public KeyEvent getKeyEvent() {
		return keyEvent;
	}
}

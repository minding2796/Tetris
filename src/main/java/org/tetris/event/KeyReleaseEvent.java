package org.tetris.event;

import java.awt.event.KeyEvent;

public class KeyReleaseEvent implements Event {
	private final KeyEvent event;
	public KeyReleaseEvent(KeyEvent event) {
		this.event = event;
	}
	public KeyEvent getKeyEvent() {
		return event;
	}
}

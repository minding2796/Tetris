package org.tetris.event;

public interface Cancellable {
	void setCancelled(boolean cancel);
	boolean isCancelled();
}

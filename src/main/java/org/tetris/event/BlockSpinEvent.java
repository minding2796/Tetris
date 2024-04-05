package org.tetris.event;

import org.tetris.block.Block;

public class BlockSpinEvent implements Event, Cancellable {
	private final Direction direction;
	private final Block block;
	private boolean cancelled;
	public BlockSpinEvent(Block block, Direction direction) {
		this.block = block;
		cancelled = false;
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public enum Direction {
		LEFT,
		RIGHT,
		DOUBLE;
	}
}

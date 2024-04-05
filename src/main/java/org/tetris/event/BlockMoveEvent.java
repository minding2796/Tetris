package org.tetris.event;

import org.tetris.block.Block;

public class BlockMoveEvent implements Event, Cancellable {
	private boolean cancelled;
	private final Direction direction;
	private final Block block;
	public BlockMoveEvent(Block block, Direction direction) {
		this.block = block;
		this.direction = direction;
	}
	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public enum Direction {
		LEFT,
		RIGHT;
	}
}

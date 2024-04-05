package org.tetris.event;

import org.tetris.block.Block;

public class BlockDropEvent implements Event, Cancellable {
	private final DropType dropType;
	private boolean cancelled;
	private final Block block;
	public BlockDropEvent(Block block, DropType dropType) {
		this.block = block;
		cancelled = false;
		this.dropType = dropType;
	}
	
	public DropType getDropType() {
		return dropType;
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
	
	public enum DropType {
		SOFT,
		HARD;
	}
}

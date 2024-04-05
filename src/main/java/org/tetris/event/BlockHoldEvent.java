package org.tetris.event;

import org.tetris.block.Block;
import org.tetris.block.BlockType;

public class BlockHoldEvent implements Event, Cancellable {
	private boolean cancelled;
	private final Block currentBlock;
	private final BlockType holdBlock;
	public BlockHoldEvent(Block currentBlock, BlockType holdBlock) {
		this.currentBlock = currentBlock;
		this.holdBlock = holdBlock;
	}
	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	public Block getCurrentBlock() {
		return currentBlock;
	}
	
	public BlockType getHoldBlock() {
		return holdBlock;
	}
}

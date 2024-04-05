package org.tetris.event;

import org.tetris.block.Block;

public class BlockRemoveEvent implements Event {
	private final Block block;
	public BlockRemoveEvent(Block block) {
		this.block = block;
	}
	
	public Block getBlock() {
		return block;
	}
}

package org.tetris.event;

import org.tetris.block.Block;

public class BlockPinEvent implements Event {
	private final Block block;
	public BlockPinEvent(Block block) {
		this.block = block;
	}
	
	public Block getBlock() {
		return block;
	}
}

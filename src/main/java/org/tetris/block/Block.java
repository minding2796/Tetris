package org.tetris.block;

import org.tetris.Tetris;
import org.tetris.event.*;

public class Block {
	BlockType type;
	Position pos;
	Rotate rotate;
	BoundingBox box;
	boolean moveToR, moveToL, softDropping;
	public boolean isSoftDropping() {
		return softDropping;
	}
	public boolean isMoveToR() {
		return moveToR;
	}
	public boolean isMoveToL() {
		return moveToL;
	}
	public BlockType getType() {
		return type;
	}
	public Position getPos() {
		return pos;
	}
	public BoundingBox getBox() {
		return box;
	}
	public void setBox(BoundingBox box) {
		this.box = box;
	}
	public Rotate getRotate() {
		return rotate;
	}
	public void spinR() {
		if (Tetris.getGame() == null) return;
		BlockSpinEvent event = new BlockSpinEvent(this, BlockSpinEvent.Direction.RIGHT);
		Tetris.getGame().callEvent(event);
		if (event.isCancelled()) return;
		rotate = rotate.r();
		box.updateRot(rotate);
		if (pos.isCollision(Tetris.getGame(), box)) {
			rotate = rotate.l();
			box.updateRot(rotate);
		}
	}
	public void spinL() {
		if (Tetris.getGame() == null) return;
		BlockSpinEvent event = new BlockSpinEvent(this, BlockSpinEvent.Direction.LEFT);
		Tetris.getGame().callEvent(event);
		if (event.isCancelled()) return;
		rotate = rotate.l();
		box.updateRot(rotate);
		if (pos.isCollision(Tetris.getGame(), box)) {
			rotate = rotate.r();
			box.updateRot(rotate);
		}
	}
	public void spinD() {
		if (Tetris.getGame() == null) return;
		BlockSpinEvent event = new BlockSpinEvent(this, BlockSpinEvent.Direction.DOUBLE);
		Tetris.getGame().callEvent(event);
		if (event.isCancelled()) return;
		rotate = rotate.d();
		box.updateRot(rotate);
		if (pos.isCollision(Tetris.getGame(), box)) {
			rotate = rotate.d();
			box.updateRot(rotate);
		}
	}
	public void hardDrop() {
		if (Tetris.getGame() == null) return;
		BlockDropEvent event = new BlockDropEvent(this, BlockDropEvent.DropType.HARD);
		Tetris.getGame().callEvent(event);
		if (event.isCancelled()) return;
		pos = pos.getLow(Tetris.getGame(), box);
		pin();
	}
	public void softDrop() {
		if (Tetris.getGame() == null) return;
		BlockDropEvent event = new BlockDropEvent(this, BlockDropEvent.DropType.SOFT);
		Tetris.getGame().callEvent(event);
		if (event.isCancelled()) return;
		softDropping = true;
	}
	public void moveR() {
		if (Tetris.getGame() == null) return;
		BlockMoveEvent event = new BlockMoveEvent(this, BlockMoveEvent.Direction.RIGHT);
		Tetris.getGame().callEvent(event);
		if (event.isCancelled()) return;
		pos.setX(pos.getX()+1);
		if (pos.isCollision(Tetris.getGame(), box)) {
			pos.setX(pos.getX()-1);
		}
		moveToR = true;
	}
	public void moveL() {
		if (Tetris.getGame() == null) return;
		BlockMoveEvent event = new BlockMoveEvent(this, BlockMoveEvent.Direction.LEFT);
		Tetris.getGame().callEvent(event);
		if (event.isCancelled()) return;
		pos.setX(pos.getX()-1);
		if (pos.isCollision(Tetris.getGame(), box)) {
			pos.setX(pos.getX()+1);
		}
		moveToL = true;
	}
	public void softDropEnd() {
		softDropping = false;
	}
	public void moveREnd() {
		moveToR = false;
	}
	public void moveLEnd() {
		moveToL = false;
	}
	public void remove() {
		if (Tetris.getGame() == null) return;
		Tetris.getGame().callEvent(new BlockRemoveEvent(this));
	}
	public void pin() {
		if (Tetris.getGame() == null) return;
		Tetris.getGame().callEvent(new BlockPinEvent(this));
	}
	public Block(BlockType type) {
		this.type = type;
		box = new BoundingBox(type);
		pos = new Position(0, Position.top(box));
		rotate = Rotate.DEG_0;
		moveToR = false;
		moveToL = false;
		softDropping = false;
	}
}

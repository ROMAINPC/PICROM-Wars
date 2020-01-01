package picrom.entity.castle;

import picrom.utils.Utils.Direction;

public class Door {
	private Direction dir;
	private boolean open;

	public Door(Direction dir, boolean open) {
		this.dir = dir;
		this.open = open;
	}

	public String toString() {
		return (open ? "Ouverte" : "Fermée") + " (direction " + dir + ")";
	}

	public Direction getDirection() {
		return dir;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}

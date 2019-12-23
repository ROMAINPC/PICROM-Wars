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
		String openS = "";
		String dirS = "";
		openS = open == false ? "Fermée" : "Ouverte";
		switch (dir) {
		case North:
			dirS = "Nord";
			break;
		case East:
			dirS = "Est";
			break;
		case South:
			dirS = "Sud";
			break;
		case West:
			dirS = "Ouest";
			break;
		}
		return " " + openS + " (direction " + dirS + ")";
	}
	
	public Direction getDir() {
		return dir;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}

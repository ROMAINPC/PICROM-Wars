package picrom.entity.castle;

import picrom.entity.Entity;
import picrom.entity.unit.Unit;
import picrom.gameboard.World;
import picrom.settings.Drawables;

public class Castle extends Entity {

	public Castle(int owner, int X, int Y, World context) {
		super(Drawables.castle, owner, X, Y, context);
	}

	private int level;
	private int treasure;

	void addUnit(Unit u) {

	}

	void rmUnit(Unit u) {

	}
}

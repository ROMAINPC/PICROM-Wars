package picrom.entity.unit;

import picrom.entity.castle.Castle;
import picrom.utils.Drawables;

public class Knight extends Unit {

	public Knight(Castle castle) {
		super(Drawables.knight, "Knight", 500, 20, 6, 3, 5, castle);
	}

}

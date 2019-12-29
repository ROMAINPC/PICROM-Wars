package picrom.entity.unit;

import picrom.entity.castle.Castle;
import picrom.utils.Drawables;

public class Pikeman extends Unit {

	public Pikeman(Castle castle) {
		super(Drawables.pikeman, "Piquier", 100, 5, 2, 1, 1, castle);
	}

}

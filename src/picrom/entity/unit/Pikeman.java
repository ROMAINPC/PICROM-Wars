package picrom.entity.unit;

import picrom.entity.castle.Castle;
import picrom.settings.Drawables;

public class Pikeman extends Unit {

	public Pikeman(Castle castle) {
		super(Drawables.pikeman, 100, 5, 2, 1, 1, castle);
	}

}

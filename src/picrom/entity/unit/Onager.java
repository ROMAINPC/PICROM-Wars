package picrom.entity.unit;

import picrom.entity.castle.Castle;
import picrom.utils.Drawables;

public class Onager extends Unit {

	public Onager(Castle castle) {
		super(Drawables.onager, "Onager", 1000, 50, 1, 5, 10, castle);
	}

}

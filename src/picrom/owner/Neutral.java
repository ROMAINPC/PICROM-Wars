package picrom.owner;

import picrom.entity.castle.Castle;

public class Neutral extends Owner implements Pensive {

	public Neutral() {
		super("NE");
	}

	public void addCastle(Castle castle) {
		if (this.getCastles().size() < 1) // Trying to add more than 1 castle will not work
			super.addCastle(castle);
	}

	public void reflect() {
		// TODO Choose best current production for defend the castles (need to know time
		// left
		// to not cancel and restart production at each call)
	}
}

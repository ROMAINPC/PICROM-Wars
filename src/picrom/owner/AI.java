package picrom.owner;

import picrom.entity.castle.Castle;

public class AI extends Owner implements Pensive {

	public AI() {
		super("IA");
	}

	public void reflect() {
		for (Castle castle : this.getCastles()) {
			// TODO Choose best current production for each castles (need to know time left
			// to not cancel and restart production at each call)

			// TODO Choose best current objective for each castles
		}
	}

}

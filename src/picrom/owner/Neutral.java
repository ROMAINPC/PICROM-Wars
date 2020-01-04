package picrom.owner;

import java.util.Random;

import picrom.entity.castle.Castle;
import picrom.entity.unit.Knight;
import picrom.entity.unit.Pikeman;
import picrom.utils.Settings;

public class Neutral extends Owner implements Pensive {

	private static Random random = Settings.SEED;

	public Neutral() {
		super("NE");
	}

	public void addCastle(Castle castle) {
		if (this.getCastles().size() < 1) // Trying to add more than 1 castle will not work
			super.addCastle(castle);
	}

	public void reflect() {
		// Because Neutrals produce Units really slowly, they have all their time to
		// make money and so don't need to improve their Castles.

		// Because Neutrals don't have ambitions, they will not produce siege units

		// When this code was writed, Pikeman have the best ratio : production time /
		// Health points. So if the castle is attacked, then Pikeman will be produce in
		// priority.

		for (Castle castle : this.getCastles()) {
			boolean underAttack = false;
			for (Owner ennemy : castle.getContext().getOwners()) {
				if (ennemy != this) {
					for (Castle ennemyCastle : ennemy.getCastles()) {
						if (ennemyCastle.getObjective() == castle) {
							underAttack = true;
							break;
						}
					}
				}
				if (underAttack)
					break;
			}

			if (castle.getProductionTimeLeft() == 0) {
				if (underAttack) {
					castle.setProduction(Pikeman.class);
				} else {
					int r = random.nextInt(2);
					if (r == 0) {
						castle.setProduction(Knight.class);
					} else if (r == 1) {
						castle.setProduction(Pikeman.class);
					}
				}

			} else {
				if (underAttack && castle.getProduction() != Pikeman.class) {
					castle.setProduction(Pikeman.class);
				}
			}
		}
	}
}

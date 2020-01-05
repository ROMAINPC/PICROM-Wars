package picrom.owner;

import java.util.Random;

import picrom.entity.castle.Castle;
import picrom.entity.unit.Knight;
import picrom.entity.unit.Onager;
import picrom.entity.unit.Pikeman;
import picrom.utils.Settings;
import picrom.utils.Utils;

/**
 * Owner type, AIs are agressives and ambitious no human players.
 */
public class AI extends Owner implements Pensive {

	private static Random random = Settings.SEED;

	private boolean changeNext;

	/**
	 * Constructor, see also {@link Owner}
	 */
	public AI() {
		super("IA");
		changeNext = false;
	}

	@Override
	public void reflect() {
		// TODO : Use door, choose good unit for defend or attack, deploy unit in
		// another castle as reinforcment, improve level of castles.

		for (Castle castle : this.getCastles()) {

			// choose a Castle to attack:
			int minDistance = castle.getContext().getWorldHeight() + castle.getContext().getWorldWidth();
			Castle nearestEnnemyCastle = null;
			for (Owner ennemy : castle.getContext().getOwners()) {
				if (ennemy != this) {
					for (Castle ennemyCastle : ennemy.getCastles()) {
						int dist = Utils.manDistance(castle.getWorldX(), castle.getWorldY(), ennemyCastle.getWorldX(),
								ennemyCastle.getWorldY());
						if (dist <= minDistance) {
							minDistance = dist;
							nearestEnnemyCastle = ennemyCastle;
						}
					}
				}
			}

			castle.setObjective(nearestEnnemyCastle);
			castle.getDoor().setOpen(true);

			// Choose production:
			// System.out.println(castle.getProductionTimeLeft());

			if (changeNext || castle.getProductionTimeLeft() == 0) {
				int r = random.nextInt(3);
				if (r == 0) {
					castle.setProduction(Knight.class);
				} else if (r == 1) {
					castle.setProduction(Pikeman.class);
				} else {
					castle.setProduction(Onager.class);
				}
				changeNext = false;
			}
			if (castle.getProductionTimeLeft() == 1) {
				changeNext = true;
			}
		}
	}

}

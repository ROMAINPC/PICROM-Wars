package picrom.owner;

import java.util.Random;

import picrom.entity.castle.Castle;
import picrom.entity.unit.Knight;
import picrom.entity.unit.Onager;
import picrom.entity.unit.Pikeman;
import picrom.utils.Settings;
import picrom.utils.Utils;

public class AI extends Owner implements Pensive {

	private static Random random = Settings.SEED;

	public AI() {
		super("IA");
	}

	public void reflect() {
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

			// Choose production:
			if (castle.getProductionTimeLeft() == 0) {
				int r = random.nextInt(3);
				if (r == 0) {
					castle.setProduction(Knight.class);
				} else if (r == 1) {
					castle.setProduction(Pikeman.class);
				} else {
					castle.setProduction(Onager.class);
				}

			}
		}
	}

}

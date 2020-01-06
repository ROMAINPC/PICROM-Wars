/*******************************************************************************
 * Copyright (C) 2019-2020 ROMAINPC
 * Copyright (C) 2019-2020 Picachoc
 * 
 * This file is part of PICROM-Wars
 * 
 * PICROM-Wars is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * PICROM-Wars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package picrom.owner;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

	/**
	 * Constructor, see also {@link Owner}
	 */
	public AI() {
		super("IA");
	}

	@Override
	public void reflect() {

		for (Castle castle : this.getCastles()) {

			// Calculate some information about other castles:
			int maxDistance = castle.getContext().getWorldHeight() + castle.getContext().getWorldWidth();
			int minEnnemyDistance = maxDistance;
			Castle nearestEnnemy = null;
			int minAllyDistance = maxDistance;
			Castle nearestAlly = null;
			int minAttackerDistance = maxDistance;
			Castle nearestAttacker = null;
			int attackers = 0;
			for (Owner owner : castle.getContext().getOwners()) {
				for (Castle otherCastle : owner.getCastles()) {
					int dist = Utils.manDistance(castle.getWorldX(), castle.getWorldY(), otherCastle.getWorldX(),
							otherCastle.getWorldY());
					if (owner != this) { // ennemy castles
						if (dist <= minEnnemyDistance) {
							minEnnemyDistance = dist;
							nearestEnnemy = otherCastle;
						}
						if (otherCastle.getObjective() == castle) {
							attackers++;
							if (nearestAttacker == null || (dist <= minAttackerDistance)) {
								nearestAttacker = otherCastle;
								minAttackerDistance = dist;
							}
						}
					} else { // ally castles
						if (otherCastle != castle && dist <= minAllyDistance) {
							minAllyDistance = dist;
							nearestAlly = otherCastle;
						}
					}
				}
			}

			// key values to make decisions.
			int attack_key = (int) ((double) Settings.AI_ATTACK_KEY * ThreadLocalRandom.current().nextDouble(0.8, 1.2));
			int defense_key = (int) ((double) Settings.AI_DEFENSE_KEY
					* ThreadLocalRandom.current().nextDouble(0.8, 1.2));

			// Choose a Castle to attack or reinforce :
			if (nearestAttacker != null) { // First of all try to strike back !
				castle.setObjective(nearestAttacker);
			} else {
				if (nearestAlly != null && nearestAlly.getGarrisonDefense() < defense_key / 2) { // Help nearest
																									// ally if
																									// it's too
																									// weak.
					castle.setObjective(nearestAlly);
				} else { // Target nearest ennemy Castle
					castle.setObjective(nearestEnnemy);
				}
			}

			// Choose production:
			if (castle.getProductionTimeLeft() <= 1) { // Normal behavior of production:
				if (castle.getLevel() < 2 && minEnnemyDistance >= maxDistance * 0.5d) { // Sector is clear, improve
																						// Castle
					castle.setProduction(Castle.class);
				} else if (castle.getLevel() < 3 && minEnnemyDistance >= maxDistance * 0.8d) { // Full peace in the area
					castle.setProduction(Castle.class);
				} else {
					int r = random.nextInt(3);
					if (castle.getObjective() == null) { // Rare situation, show who has the biggest.
						castle.setProduction(Onager.class);
					} else if (castle.getObjective().getOwner() == castle.getOwner()
							|| castle.getObjective() == nearestAttacker) { // Produce cheap Unit or quick Unit to help
																			// Ally or to strike back.
						if (r == 0)
							castle.setProduction(Knight.class);
						else
							castle.setProduction(Pikeman.class);
					} else { // prepared assault
						if (r == 0)
							castle.setProduction(Onager.class);
						else
							castle.setProduction(Knight.class); // Knight have the best ratio productionTime/Damage
					}
				}
			} else { // Emergency production, priority to Pikeman, they are cheap to produce and have
						// best ratio productionTime/HealthPoints.
				if (attackers > 2 && castle.getProduction() != Pikeman.class) {

					castle.setProduction(Pikeman.class);
				}
			}

			// Decide to open the door to go the objectiv, or close the
			// door to keep stacking Units in defense.
			if (castle.getObjective() == null) {
				castle.getDoor().setOpen(false);
			} else {
				if (castle.getDoor().isOpen()) { // decide if its time to close the door
					if (castle.getGarrisonDefense() < defense_key)
						castle.getDoor().setOpen(false);
				} else { // decide if its time to send Units
					if (castle.getGarrisonAttack() > attack_key)
						castle.getDoor().setOpen(true);
				}
			}

		}
	}

}

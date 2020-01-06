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

import picrom.entity.castle.Castle;
import picrom.entity.unit.Knight;
import picrom.entity.unit.Pikeman;
import picrom.utils.Settings;

/**
 * Owner type, Neutrals are no human players whithout ambitious, will attack
 * anybody. Can only have one Castle.
 */
public class Neutral extends Owner implements Pensive {

	private static Random random = Settings.SEED;

	/**
	 * Constructor, see also {@link Owner}
	 */
	public Neutral() {
		super("NE");
	}

	/**
	 * Overrided method, do nothing if Neutral already have a Castle.
	 * 
	 * @see Owner#addCastle(Castle)
	 */
	public void addCastle(Castle castle) {
		if (this.getCastles().size() < 1) // Trying to add more than 1 castle will not work
			super.addCastle(castle);
	}

	@Override
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

			if (castle.getProductionTimeLeft() <= 1) {
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

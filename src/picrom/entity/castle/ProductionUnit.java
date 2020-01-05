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
package picrom.entity.castle;

import picrom.owner.Neutral;
import picrom.utils.Settings;

public class ProductionUnit {

	private Castle castle;

	private Producible currentProduction;
	private boolean produced;

	private int timeLeft;

	public ProductionUnit(Castle castle) {
		this.castle = castle;
		produced = true;
		timeLeft = 0;
	}

	public void update() {
		if (!produced) {
			timeLeft--;
			if (timeLeft <= 0 && castle.getTreasure() >= currentProduction.getProductionCost()) {
				castle.setTreasure(castle.getTreasure() - currentProduction.getProductionCost()); // Apply cost
				currentProduction.produce(castle);
				produced = true;
			}

		}
	}

	public void setProduction(Producible production) {
		currentProduction = production;
		produced = false;
		if (production != null) {
			int multiplier = castle.getOwner() instanceof Neutral ? Settings.NEUTRAL_PRODUCTION_MULTIPLIER : 1;
			timeLeft = production.getProductionTime() * multiplier;
		}

	}

	public Producible getProduction() {
		return currentProduction;
	}

	public boolean isProduced() {
		return produced;
	}

	public void stop() {
		produced = true;
		timeLeft = 0;
	}

	/**
	 * Get the time remaining to finish production.
	 * 
	 * @return Number of updates of this production unit needed to finish production
	 *         (not include money cost). Could be negativ (waiting for money).
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	public int getCost() {
		return currentProduction == null ? 0 : currentProduction.getProductionCost();
	}

}

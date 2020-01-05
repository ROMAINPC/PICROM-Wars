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

/**
 * Producible classes are Object that can be produce in ProductionUnit.
 * 
 * @see picrom.entity.castle.ProductionUnit
 */
public interface Producible {
	/**
	 * Do the action to concretly produce the Producible.
	 * 
	 * @param castle Castle containing the ProductionUnit
	 */
	public void produce(Castle castle);

	/**
	 * @return Money needed to produce this Producible.
	 */
	public int getProductionCost();

	/**
	 * @return Number of turns needed to produce this Producible.
	 */
	public int getProductionTime();

}

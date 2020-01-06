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

import picrom.utils.Utils.Direction;

/**
 * Doors are sticked to the side of a Castle and so have an orientation. Can be
 * open or closed.
 * 
 * @see picrom.entity.castle.Castle
 */
public class Door {
	private Direction dir;
	private boolean open;

	/**
	 * Constructor
	 * 
	 * @param dir  Door orientation
	 * @param open First door statu
	 */
	public Door(Direction dir, boolean open) {
		this.dir = dir;
		this.open = open;
	}

	/**
	 * @return Door orientation
	 */
	public Direction getDirection() {
		return dir;
	}

	/**
	 * @return Door statu
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * Set Door statu
	 * 
	 * @param open new statu
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	@Override
	public String toString() {
		return "Door (" + dir + ", " + (open?"open":"close") + ")";
	}
}

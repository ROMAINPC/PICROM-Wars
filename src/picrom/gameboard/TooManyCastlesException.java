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
package picrom.gameboard;

/**
 * Exception used to inform that a World try to generate too many Castles. Use
 * it to limit number of castles generated.
 * 
 * @see picrom.gameboard.World
 * @see picrom.entity.castle.Castle
 */
public class TooManyCastlesException extends Exception {
	/**
	 * Constructor, also print error message in standard error exit.
	 */
	public TooManyCastlesException() {
		System.err.println(
				"Exception trigered due to too long castle generation, try to reduce number of castles or spacing between them or increase world size.");
	}
}

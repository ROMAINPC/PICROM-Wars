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
package picrom.utils;

import java.util.Random;

import javafx.util.Duration;

/**
 * Class with constants static values used by all the game.
 * 
 * Edit here gameboard or eventually gameplay settings.
 */
public class Settings {
	// gameplay settings:
	public static final Duration TURN_DURATION = Duration.millis(100);
	public static final int MAX_UNITS_OUT_BY_TURN = 3;
	public static final int INCOME_MULTIPLIER = 10;
	public static final int UNITS_GRID_SIZE = 10;
	public static final int START_TREASURE = 0;
	public static final int NEUTRAL_PRODUCTION_MULTIPLIER = 10;
	public static final int NEUTRAL_START_GARRISON = 30; // HPs
	public static final int START_GARRISON = 10; // HPs

	// game board settings:
	public static final int WORLD_WIDTH = 18;
	public static final int WORLD_HEIGHT = 15;
	public static final int NUMBER_OF_AIS = 3;
	public static final int NUMBER_OF_BARONS = 6;
	public static final int MINIMAL_CASTLE_DISTANCE = 2;

	// graphical settings:
	public static final double DEFAULT_SCENE_WIDTH = 1100;
	public static final double DEFAULT_SCENE_HEIGHT = 700;
	public static final double WORLD_WIDTH_RATIO = 0.7;

	// game seed:
	public static final Random SEED = new Random();
	
	//save settings:
	public static final String SAVES_EXTENSION = "pw";

}

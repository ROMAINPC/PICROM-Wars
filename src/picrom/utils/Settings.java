package picrom.utils;

import java.util.Random;

import javafx.util.Duration;

/**
 * Class with constants values used by all the game.
 */
public class Settings {
	// gameplay settings:
	public static final Duration TURN_DURATION = Duration.millis(1000);
	public static final int MAX_UNITS_OUT_BY_TURN = 3;
	public static final int INCOME_MULTIPLIER = 10;

	// game board settings:
	public static final int WORLD_WIDTH = 18;
	public static final int WORLD_HEIGHT = 15;
	public static final int NUMBER_OF_AIS = 3;
	public static final int NUMBER_OF_BARONS = 6;
	public static final int MINIMAL_CASTLE_DISTANCE = 2;

	// graphical settings:
	public static final double DEFAULT_SCENE_WIDTH = 900;
	public static final double DEFAULT_SCENE_HEIGHT = 750;
	public static final double WORLD_WIDTH_RATIO = 0.8;

	// game seed:
	public static final Random SEED = new Random();

}

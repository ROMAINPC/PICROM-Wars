package picrom.settings;

import javafx.scene.image.Image;

/**
 * Class for load all game pictures.
 */
public class Drawables {

	public static Image worldBackground;
	public static Image castle;
	public static Image onager;
	public static Image knight;
	public static Image pikeman;

	public Drawables() {
		worldBackground = new Image("Drawables/world_background.png");
		castle = new Image("Drawables/castle.png");
		onager = new Image("Drawables/world_background.png");
		knight = new Image("Drawables/world_background.png");
		pikeman = new Image("Drawables/world_background.png");
	}

}

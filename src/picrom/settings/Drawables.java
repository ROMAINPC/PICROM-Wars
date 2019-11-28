package picrom.settings;

import javafx.scene.image.Image;

/**
 * Class for load all game pictures.
 */
public class Drawables {

	public static Image worldBackground;
	public static Image castle;

	public Drawables() {
		worldBackground = new Image("Drawables/world_background.png");
		castle = new Image("Drawables/castle.png");
	}

}

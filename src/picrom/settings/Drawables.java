package picrom.settings;

import javafx.scene.image.Image;

/**
 * Class for load all game pictures.
 */
public class Drawables {

	public static Image worldBackground;
	public static EntityAssets castle;
	public static EntityAssets onager;
	public static EntityAssets knight;
	public static EntityAssets pikeman;

	public Drawables() {
		worldBackground = new Image("Drawables/world_background.png");

		castle = new EntityAssets("castle");
		onager = new EntityAssets("knight");// TODO
		knight = new EntityAssets("knight");
		pikeman = new EntityAssets("knight");// TODO
	}

	public class EntityAssets {
		private Image image;
		private Image mask;

		private EntityAssets(String name) {
			image = new Image("Drawables/" + name + ".png");
			mask = new Image("Drawables/" + name + "_mask.png");
		}

		public Image getImage() {
			return image;
		}

		public Image getMask() {
			return mask;
		}
	}

}

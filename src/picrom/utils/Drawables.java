package picrom.utils;

import javafx.scene.image.Image;

/**
 * Class for load all game pictures.
 */
public class Drawables {

	public static Image worldBackground;
	public static Image infosBackground;
	public static Image circled;
	public static Image hammer;
	public static Image crown;
	public static Image treasure;
	public static EntityAssets castle;
	public static EntityAssets onager;
	public static EntityAssets knight;
	public static EntityAssets pikeman;

	public Drawables() {
		worldBackground = new Image("Drawables/world_background.png");
		infosBackground = new Image("Drawables/infos_background.png");
		circled = new Image("Drawables/circled.png");
		hammer = new Image("Drawables/hammer.png");
		crown = new Image("Drawables/crown.png");
		treasure = new Image("Drawables/treasure.png");

		castle = new EntityAssets("castle");
		onager = new EntityAssets("onager");
		knight = new EntityAssets("knight");
		pikeman = new EntityAssets("pikeman");
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

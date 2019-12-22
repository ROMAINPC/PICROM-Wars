package picrom.utils;

import java.util.Random;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Utils {
	private static Random r = Settings.SEED;

	public static enum OwnerType {
		Player("HU"), AI("AI"), Baron("BR");

		private String string;

		private OwnerType(String str) {
			string = str;
		}

		public String toString() {
			return string;
		}
	}

	public static enum Direction {
		North(0, -1), East(1, 0), South(0, 1), West(-1, 0);

		public int dx, dy;

		private Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}
	}

	public static double map(double value, double start, double stop, double targetStart, double targetStop) {
		return targetStart + (targetStop - targetStart) * ((value - start) / (stop - start));
	}

	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	public static int manDistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x2 - x1) + Math.abs(y2 - y1);
	}

	public static String generateKingdomName() {
		String name = "";
		String alphabet = "aaaazeeeertyuuuuiiiioooopqsdfghjklmwxcvbn";
		for (int i = 0; i < r.nextInt(5) + 4; i++) {
			name += i == 0 ? Character.toUpperCase(alphabet.charAt(r.nextInt(alphabet.length())))
					: alphabet.charAt(r.nextInt(alphabet.length()));
		}
		return "Kingdom of " + name;
	}

	public static void colorize(ImageView image, Color color) {
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setHue(Utils.map((color.getHue() + 180) % 360, 0, 360, -1, 1));
		colorAdjust.setSaturation(color.getSaturation());
		colorAdjust.setBrightness(Utils.map(color.getBrightness(), 0, 1, -1, 0));
		image.setEffect(colorAdjust);
	}

}

package picrom.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Utils {
	private static Random r = Settings.SEED;

	public static enum Direction {
		North(0, -1, "Nord"), East(1, 0, "Est"), South(0, 1, "Sud"), West(-1, 0, "Ouest");

		private int x, y;
		private String str;
		private static List<Direction> DIRS = Arrays.asList(values());
		private static final int SIZE = DIRS.size();

		private Direction(int x, int y, String str) {
			this.x = x;
			this.y = y;
			this.str = str;
		}

		public static Direction randomDirection() {
			return DIRS.get(r.nextInt(SIZE));
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public String toString() {
			return str;
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
		String[] syllables = { "li", "la", "le", "chto", "chta", "fra", "fro", "fru", "tro", "tru", "tra", "tri", "nou",
				"mou", "kou", "ing", "bra", "bre", "bri", "bru", "ka", "ke", "ki", "ku", "vish", "vik", "gla", "glo",
				"gle" };
		for (int i = 0; i < r.nextInt(3) + 2; i++) {
			name += syllables[r.nextInt(syllables.length)];
		}
		name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
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

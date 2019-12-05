package picrom.settings;

import java.util.Random;

public class Utils {
	private static Random r = Settings.SEED;

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

}

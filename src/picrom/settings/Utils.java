package picrom.settings;

public class Utils {

	public static double map(double value, double start, double stop, double targetStart, double targetStop) {
		return targetStart + (targetStop - targetStart) * ((value - start) / (stop - start));
	}

	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
	public static int manDistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x2-x1) + Math.abs(y2-y1);
	}

}

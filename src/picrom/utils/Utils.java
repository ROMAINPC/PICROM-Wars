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

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Utils {
	private static Random r = Settings.SEED;

	/**
	 * Cardinal points.
	 */
	public static enum Direction {
		North(0, -1, "Nord"), East(1, 0, "Est"), South(0, 1, "Sud"), West(-1, 0, "Ouest");

		private int x, y;
		private String str;
		private static List<Direction> DIRS = Arrays.asList(values());
		private static final int SIZE = DIRS.size();

		/**
		 * Cardinal point constructor.
		 * 
		 * @param x   -1,0 or 1 indicate direcion on horizontal axis.
		 * @param y   -1,0 or 1 indicate direcion on vertical axis.
		 * @param str Name of the point
		 */
		private Direction(int x, int y, String str) {
			this.x = x;
			this.y = y;
			this.str = str;
		}

		/**
		 * @return Random direction among the enumeration.
		 */
		public static Direction randomDirection() {
			return DIRS.get(r.nextInt(SIZE));
		}

		/**
		 * @return -1,0 or 1 indicate direcion on horizontal axis.
		 */
		public int getX() {
			return x;
		}

		/**
		 * @return -1,0 or 1 indicate direcion on vertical axis.
		 */
		public int getY() {
			return y;
		}

		@Override
		public String toString() {
			return str;
		}
	}

	/**
	 * @param value       Value in the start range.
	 * @param start       First value in the start range.
	 * @param stop        Last value in the start range.
	 * @param targetStart First value in the new range.
	 * @param targetStop  Last value in the new range.
	 * @return Value adapted in the new range.
	 */
	public static double map(double value, double start, double stop, double targetStart, double targetStop) {
		return targetStart + (targetStop - targetStart) * ((value - start) / (stop - start));
	}

	/**
	 * @param x1 X coordinate of first case.
	 * @param y1 Y coordinate of first case.
	 * @param x2 X coordinate of second case.
	 * @param y2 Y coordinate of second case.
	 * @return Manhattan distance between two cases.
	 */
	public static int manDistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x2 - x1) + Math.abs(y2 - y1);
	}

	/**
	 * See {@link Utils#manDistance(int, int, int, int) }
	 * 
	 * @param x1 X coordinate of first case.
	 * @param y1 Y coordinate of first case.
	 * @param x2 X coordinate of second case.
	 * @param y2 Y coordinate of second case.
	 * @return Manhattan distance between two cases.
	 */
	public static int manDistance(double x1, double y1, double x2, double y2) {
		return manDistance((int) x1, (int) y1, (int) x2, (int) y2);
	}

	/**
	 * @return Random name, which starts with with "Royaume de"
	 */
	public static String generateKingdomName() {
		String name = "";
		String[] syllables = { "li", "la", "le", "chto", "chta", "fra", "fro", "fru", "tro", "tru", "tra", "tri", "nou",
				"mou", "kou", "ing", "bra", "bre", "bri", "bru", "ka", "ke", "ki", "ku", "vish", "vik", "gla", "glo",
				"gle" };
		for (int i = 0; i < r.nextInt(3) + 2; i++) {
			name += syllables[r.nextInt(syllables.length)];
		}
		name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		return "Royaume de " + name;
	}

	/**
	 * Colorize ImageView passed with color, the Image need to be white and
	 * transparent only to apply good color.
	 * 
	 * @param image ImageView to colorize
	 * @param color Target color
	 * @see javafx.scene.effect.ColorAdjust
	 */
	public static void colorize(ImageView image, Color color) {
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setHue(Utils.map((color.getHue() + 180) % 360, 0, 360, -1, 1));
		colorAdjust.setSaturation(color.getSaturation());
		colorAdjust.setBrightness(Utils.map(color.getBrightness(), 0, 1, -1, 0));
		image.setEffect(colorAdjust);
	}

}

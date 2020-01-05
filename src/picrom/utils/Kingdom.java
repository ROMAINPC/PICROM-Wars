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
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

/**
 * Enumeration of Kingdom identities.
 */
public enum Kingdom {
	FRANCE("Royaume de France", Color.BLUE), ENGLAND("Royaume d'Angleterre", Color.WHITE),
	CASTILE("Royaume de Castile", Color.RED), GERMANY("Empire Romain Germanique", Color.ORANGE),
	VATICAN("États pontificaux", Color.YELLOW), PERSIAN("Empire Perse", Color.GREEN),
	MONGOL("Empire Mongol", Color.rgb(204, 255, 255)), ISLAMICSTATE("Etat Islamique", Color.BLACK),
	// When it is undefined, a random name and color will be generated.
	UNDEFINED("", Color.BLACK);

	private String name;
	private Color color;
	private static int index = 0;

	private static List<Kingdom> kingdoms = Arrays.asList(values());
	private static final Random r = Settings.SEED;

	/**
	 * Enum constructor.
	 * 
	 * @param name  Name of the Kingdom
	 * @param color Color of the Kingdon, to show proudly.
	 */
	Kingdom(String name, Color color) {
		this.name = name;
		this.color = color;
	}

	/**
	 * @return Name of the Kingdom, or generates name if Kingdom is UNDEFINED.
	 * @see Utils#generateKingdomName()
	 */
	public String getName() {
		if (this != UNDEFINED)
			return name;
		return Utils.generateKingdomName();
	}

	/**
	 * @return Color of the Kingom, or generates random color if Kingdom is
	 *         UNDEFINED.
	 */
	public Color getColor() {
		if (this != UNDEFINED)
			return color;
		return Color.hsb(r.nextInt(360), 1.0, 1.0);
	}

	/**
	 * Used to pickup a random Kingdom, shuffle kingdoms at first call and then
	 * return kingdoms in the enumeration.
	 * 
	 * @return Random Kingdom, UNDEFINED when all kingdoms will be choosen at
	 *         random.
	 */
	public static Kingdom randomKingdom() {
		if (index == 0)
			Collections.shuffle(kingdoms);
		if (index == kingdoms.size())
			return UNDEFINED;
		index++;
		return kingdoms.get(index - 1);
	}
}

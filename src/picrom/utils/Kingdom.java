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

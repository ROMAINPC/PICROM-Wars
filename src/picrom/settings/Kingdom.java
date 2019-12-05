package picrom.settings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

public enum Kingdom {
	FRANCE("Kingdom of France",Color.BLUE),
	ENGLAND("Kingdom of England",Color.WHITE),
	CASTILE("Kingdom of Castile",Color.RED),
	GERMANY("Holy Roman Empire",Color.ORANGE),
	VATICAN("Papal States",Color.YELLOW),
	PERSIAN("Persian Empire",Color.GREEN),
	MONGOL("Mongol Empire",Color.rgb(204, 255, 255)),
	//When it is undefined, a random name and color will be generated.
	UNDEFINED("",Color.BLACK);
	
	private String name;
	private Color color;
	private static int nbPlayers = 0;
	
	private static final List<Kingdom> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random r = Settings.SEED;
	
	Kingdom(String name, Color color){
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		if(this != UNDEFINED)
			return name;
		return Utils.generateKingdomName();
	}
	
	public Color getColor() {
		if(this != UNDEFINED)
			return color;
		return Color.hsb(r.nextInt(360), 1.0, 1.0);
	}

	public static Kingdom randomKingdom() {
		if(nbPlayers != VALUES.size()) {
			nbPlayers++;
			return VALUES.get(r.nextInt(SIZE));}
		return UNDEFINED;
	}
}

package picrom.entity;

import java.util.Random;

import javafx.scene.paint.Color;
import picrom.settings.Settings;

public class Owner {
	private static Random random = new Random();
	
	private double hue; //betwenn -1.0 and 1.0
	private String name;//not use as ID, prefer object reference
	private Settings.OwnerType ownerType;

	public Owner(double hue, String name, Settings.OwnerType ownerType) {
		hue = hue > 1.0 ? 1.0 : hue;
		hue = hue > 1.0 ? 1.0 : hue;
		this.hue = hue;
		this.name = name;
		this.ownerType = ownerType;
	}

	public Owner(Settings.OwnerType ownerType) {
		this(0, null, ownerType);
		this.hue = random.nextDouble() * 2 - 1; 
	}

	public double getHue() {
		return hue;
	}

	public void setHue(double hue) {
		this.hue = hue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Settings.OwnerType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Settings.OwnerType ownerType) {
		this.ownerType = ownerType;
	}

}

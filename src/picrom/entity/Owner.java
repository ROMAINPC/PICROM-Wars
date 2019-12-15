package picrom.entity;

import java.util.Random;

import javafx.scene.paint.Color;
import picrom.settings.Kingdom;
import picrom.settings.Settings;

public class Owner {
	private static Random random = Settings.SEED;

	private Color color;
	private String name;// not use as ID, prefer object reference
	private Settings.OwnerType ownerType;

	public Owner(Color color, String name, Settings.OwnerType ownerType) {
		this.color = color;
		this.name = name;
		this.ownerType = ownerType;
	}

	public Owner(Settings.OwnerType ownerType) {
		Kingdom k = Kingdom.randomKingdom();
		this.color = k.getColor();
		this.name = k.getName();
		this.ownerType = ownerType;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
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
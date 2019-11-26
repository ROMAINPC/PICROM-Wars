package picrom.entity;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Entity extends ImageView {
	private int worldX;
	private int worldY;
	private int owner; // TODO create owner type
	protected Scene context;

	protected Entity(Image img, int owner, int X, int Y, Scene world) {
		this.setImage(img);
		this.owner = owner;
		worldX = X;
		worldY = Y;
		context = world;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getWorldX() {
		return worldX;
	}

	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}

	public int getWorldY() {
		return worldY;
	}

	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}
}

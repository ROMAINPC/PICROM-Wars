package picrom.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import picrom.gameboard.World;
import picrom.settings.Settings;

public abstract class Entity extends ImageView {
	private SimpleDoubleProperty worldX;
	private SimpleDoubleProperty worldY;
	private int owner; // TODO create owner type
	protected World context;

	protected Entity(Image img, int owner, int X, int Y, World world) {
		this.setImage(img);
		this.owner = owner;
		worldX = new SimpleDoubleProperty(X);
		worldY = new SimpleDoubleProperty(Y);
		context = world;
		// default binding:
		this.fitWidthProperty().bind(context.widthProperty().divide(Settings.WORLD_WIDTH));
		this.fitHeightProperty().bind(context.heightProperty().divide(Settings.WORLD_HEIGHT));
		this.layoutXProperty().bind(this.fitWidthProperty().multiply(this.worldX));
		this.layoutYProperty().bind(this.fitHeightProperty().multiply(this.worldY));
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getWorldX() {
		return (int) worldX.get();
	}

	public void setWorldX(int worldX) {
		this.worldX.set(worldX);
	}

	public int getWorldY() {
		return (int) worldY.get();
	}

	public void setWorldY(int worldY) {
		this.worldY.set(worldY);
	}
}

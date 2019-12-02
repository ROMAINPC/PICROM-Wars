package picrom.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import picrom.entity.castle.Castle;
import picrom.gameboard.World;
import picrom.settings.Settings;

public abstract class Entity extends ImageView {
	private SimpleDoubleProperty worldX;
	private SimpleDoubleProperty worldY;
	private Owner owner; // TODO create owner type
	private int prodCost, prodTime;

	protected World context;

	protected Entity(Image img, Owner owner, int X, int Y, int prodCost, int prodTime, World world) {
		this.setImage(img);
		this.owner = owner;
		worldX = new SimpleDoubleProperty(X);
		worldY = new SimpleDoubleProperty(Y);
		this.prodCost = prodCost;
		this.prodTime = prodTime;
		context = world;
		// default binding:
		this.fitWidthProperty().bind(context.widthProperty().divide(Settings.WORLD_WIDTH));
		this.fitHeightProperty().bind(context.heightProperty().divide(Settings.WORLD_HEIGHT));
		this.layoutXProperty().bind(this.fitWidthProperty().multiply(this.worldX));
		this.layoutYProperty().bind(this.fitHeightProperty().multiply(this.worldY));
	}

	protected Entity(Image img, int prodCost, int prodTime, Castle owner) {
		this(img, owner.getOwner(), owner.getWorldX(), owner.getWorldY(), prodCost, prodTime, owner.context);
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
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

	public int getProdCost() {
		return prodCost;
	}

	public void setProdCost(int prodCost) {
		this.prodCost = prodCost;
	}

	public int getProdTime() {
		return prodTime;
	}

	public void setProdTime(int prodTime) {
		this.prodTime = prodTime;
	}
}

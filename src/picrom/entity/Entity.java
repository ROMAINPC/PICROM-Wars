package picrom.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import picrom.entity.castle.Castle;
import picrom.gameboard.World;
import picrom.settings.Drawables.EntityAssets;
import picrom.settings.Settings;
import picrom.settings.Utils;

public abstract class Entity extends Group {
	private SimpleDoubleProperty worldX;
	private SimpleDoubleProperty worldY;
	private Owner owner; // TODO create owner type
	private int prodCost, prodTime;

	protected World context;

	private ImageView image;
	private ImageView mask;

	protected Entity(EntityAssets assets, Owner owner, int X, int Y, int prodCost, int prodTime, World world) {
		image = new ImageView(assets.getImage());
		mask = new ImageView(assets.getMask());
		this.owner = owner;
		worldX = new SimpleDoubleProperty(X);
		worldY = new SimpleDoubleProperty(Y);
		this.prodCost = prodCost;
		this.prodTime = prodTime;
		context = world;

		// default binding
		image.fitWidthProperty().bind(context.widthProperty().divide(Settings.WORLD_WIDTH));
		image.fitHeightProperty().bind(context.heightProperty().divide(Settings.WORLD_HEIGHT));
		image.layoutXProperty().bind(image.fitWidthProperty().multiply(this.worldX));
		image.layoutYProperty().bind(image.fitHeightProperty().multiply(this.worldY));
		mask.fitWidthProperty().bind(context.widthProperty().divide(Settings.WORLD_WIDTH));
		mask.fitHeightProperty().bind(context.heightProperty().divide(Settings.WORLD_HEIGHT));
		mask.layoutXProperty().bind(image.fitWidthProperty().multiply(this.worldX));
		mask.layoutYProperty().bind(image.fitHeightProperty().multiply(this.worldY));

		// color effect:
		applyColor(owner.getColor());

		this.getChildren().addAll(image, mask); // add assets
	}
	
	

	protected Entity(EntityAssets img, int prodCost, int prodTime, Castle owner) {
		this(img, owner.getOwner(), owner.getWorldX(), owner.getWorldY(), prodCost, prodTime, owner.context);
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
		applyColor(owner.getColor());
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
	
	private void applyColor(Color c) {
		//colorize white mask:
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setHue(Utils.map( (c.getHue() + 180) % 360, 0, 360, -1, 1));
		colorAdjust.setSaturation(c.getSaturation());
		colorAdjust.setBrightness(Utils.map( c.getBrightness(), 0, 1, -1, 0));
		mask.setEffect(colorAdjust);
	}
}

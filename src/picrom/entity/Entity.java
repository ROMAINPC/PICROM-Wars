package picrom.entity;

import java.io.IOException;
import java.io.Serializable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import picrom.entity.castle.Castle;
import picrom.gameboard.World;
import picrom.owner.Owner;
import picrom.utils.Drawables.EntityAssets;
import picrom.utils.Settings;
import picrom.utils.Utils;

public abstract class Entity extends Group implements Serializable {
	transient private SimpleDoubleProperty worldX;
	transient private SimpleDoubleProperty worldY;
	//Updated just for the serialization
	private double worldXSerial;
	private double worldYSerial;
	private Owner owner;

	private World context;

	transient protected ImageView image;
	transient protected ImageView mask;

	protected Entity(EntityAssets assets, Owner owner, double X, double Y, World world) {
		image = new ImageView(assets.getImage());
		mask = new ImageView(assets.getMask());
		this.owner = owner;
		worldX = new SimpleDoubleProperty(X);
		worldY = new SimpleDoubleProperty(Y);
		context = world;

		// default binding
		image.fitWidthProperty().bind(getContext().widthProperty().divide(Settings.WORLD_WIDTH));
		image.fitHeightProperty().bind(getContext().heightProperty().divide(Settings.WORLD_HEIGHT));
		image.layoutXProperty().bind(getContext().xProperty().add(image.fitWidthProperty().multiply(this.worldX)));
		image.layoutYProperty().bind(getContext().yProperty().add(image.fitHeightProperty().multiply(this.worldY)));
		mask.fitWidthProperty().bind(getContext().widthProperty().divide(Settings.WORLD_WIDTH));
		mask.fitHeightProperty().bind(getContext().heightProperty().divide(Settings.WORLD_HEIGHT));
		mask.layoutXProperty().bind(getContext().xProperty().add(image.fitWidthProperty().multiply(this.worldX)));
		mask.layoutYProperty().bind(getContext().yProperty().add(image.fitHeightProperty().multiply(this.worldY)));

		// color effect:
		applyColor(owner.getColor());

		this.getChildren().addAll(image, mask); // add assets
	}

	protected Entity(EntityAssets img, Castle owner) {
		this(img, owner.getOwner(), owner.getWorldX(), owner.getWorldY(), owner.getContext());
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
		applyColor(owner.getColor());
	}

	public double getWorldX() {
		return worldX.get();
	}

	public void setWorldX(double worldX) {
		this.worldX.set(worldX);
	}

	public double getWorldY() {
		return worldY.get();
	}

	public void setWorldY(double worldY) {
		this.worldY.set(worldY);
	}

	public SimpleDoubleProperty worldXProperty() {
		return worldX;
	}

	public SimpleDoubleProperty worldYProperty() {
		return worldY;
	}

	private void applyColor(Color c) {
		// colorize white mask:
		Utils.colorize(mask, c);
	}

	public World getContext() {
		return context;
	}
	
	 private void writeObject(java.io.ObjectOutputStream out) throws IOException{
		 //worldX and worldY are not serializable and thus need a specific treatment
		 worldXSerial = getWorldX();
		 worldYSerial = getWorldY();
		 //Default serialization
		 out.defaultWriteObject();
	 }
	 
	 private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
		 //Default de-serialization
		 in.defaultReadObject();
		 worldX = new SimpleDoubleProperty(worldXSerial);
		 worldY = new SimpleDoubleProperty(worldYSerial);
	 }

}

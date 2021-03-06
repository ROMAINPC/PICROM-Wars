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
package picrom.entity;

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

/**
 * An Entity is an Object to add in a World. That can be for instance characters
 * or building.
 * 
 * Entity has an Owner (see {@link picrom.owner.Owner}) which owns it.
 * 
 * Entity is also a JavaFX component, it comes with two ImageView, mask and main
 * image (see {@link picrom.utils.Drawables.EntityAssets}). Graphics will be
 * correclty bind in the World which contains this Entity.
 */
public abstract class Entity extends Group {
	private SimpleDoubleProperty worldX;
	private SimpleDoubleProperty worldY;
	private Owner owner;

	private World context;

	protected ImageView image; // protected because some extended classes cans modify their own graphical part.
	protected ImageView mask;

	/**
	 * Constructor to add an Entity in the world.
	 * 
	 * @param assets Image and Image mask for the graphical part
	 * @param owner  Owner
	 * @param X      X position in the World coordinate system.
	 * @param Y      Y position in the World coordinate system.
	 * @param world  World within the Entity will evolute.
	 */
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

	/**
	 * Special constructor to add an Entity to the world direclty in a Castle.
	 * Doesn't hide Images. Used for instance for soldiers, characters, ...
	 * 
	 * @param img   Image and Image mask for the graphical part
	 * @param owner the castle which will contain the Entity
	 */
	protected Entity(EntityAssets img, Castle owner) {
		this(img, owner.getOwner(), owner.getWorldX(), owner.getWorldY(), owner.getContext());
	}

	/**
	 * @return Entity owner
	 */
	public Owner getOwner() {
		return owner;
	}

	/**
	 * Define new owner for this Entity
	 * 
	 * @param owner the Owner
	 */
	public void setOwner(Owner owner) {
		this.owner = owner;
		applyColor(owner.getColor());
	}

	/**
	 * @return X position in the World coordinate system.
	 */
	public double getWorldX() {
		return worldX.get();
	}

	/**
	 * Set X position in the World coordinate system.
	 * 
	 * @param worldX coordinate
	 */
	public void setWorldX(double worldX) {
		this.worldX.set(worldX);
	}

	/**
	 * @return Y position in the World coordinate system.
	 */
	public double getWorldY() {
		return worldY.get();
	}

	/**
	 * Set Y position in the World coordinate system.
	 * 
	 * @param worldY coordinate
	 */
	public void setWorldY(double worldY) {
		this.worldY.set(worldY);
	}

	/**
	 * @return X position in the World coordinate system as a dynamic value.
	 */
	public SimpleDoubleProperty worldXProperty() {
		return worldX;
	}

	/**
	 * @return Y position in the World coordinate system as a dynamic value.
	 */
	public SimpleDoubleProperty worldYProperty() {
		return worldY;
	}

	/**
	 * Colorize mask.
	 * 
	 * @param c Color
	 */
	private void applyColor(Color c) {
		// colorize white mask:
		Utils.colorize(mask, c);
	}

	/**
	 * @return The World in which the Entity is.
	 */
	public World getContext() {
		return context;
	}

	@Override
	public String toString() {
		return "Entity, (" + getWorldX() + ", " + getWorldY() + "), Owner: " + owner;
	}

}

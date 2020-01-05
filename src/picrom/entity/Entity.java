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

public abstract class Entity extends Group {
	private SimpleDoubleProperty worldX;
	private SimpleDoubleProperty worldY;
	private Owner owner;

	private World context;

	protected ImageView image;
	protected ImageView mask;

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

}

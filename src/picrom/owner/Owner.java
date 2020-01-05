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
package picrom.owner;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import picrom.entity.castle.Castle;
import picrom.utils.Kingdom;

/**
 * An Owner is a player (AI or human). It has a its own identity (name and
 * color) and it owns Casltes.
 * 
 * This class is also a JavaFX component, a small bar with some informations
 * about the owner. Show it for instance in a VBox.
 */
public class Owner extends StackPane implements Serializable{

	private static final long serialVersionUID = 1L;
	
	transient private Color color;
	//Only used for serialization and deserialization
	private double[] colorHSB;
	private String name; // not use as ID, prefer object reference
	private String ownerType;

	// castles owned
	private List<Castle> castles;

	transient private Label numberL;
	transient private Line crossed;

	/**
	 * Constructor to create an Owner from a Kingdom identity.
	 * 
	 * @param kingdom   Identity
	 * @param ownerType String printed in UI: type of the owner(human, ai, ...)
	 * @see picrom.utils.Kingdom
	 */
	public Owner(Kingdom kingdom, String ownerType) {
		this(kingdom.getColor(), kingdom.getName(), ownerType);
	}

	/**
	 * Constructor to create an Owner.
	 * 
	 * @param color     Color
	 * @param name      Name
	 * @param ownerType String printed in UI: type of the owner(human, ai, ...)
	 */
	public Owner(Color color, String name, String ownerType) {
		this.color = color;
		this.name = name;
		this.ownerType = ownerType;
		castles = new LinkedList<Castle>();
		setUI();
	}

	/**
	 * Constructor to create an Owner with random identity (name and color).
	 * 
	 * @param ownerType String printed in UI: type of the owner(human, ai, ...)
	 */
	public Owner(String ownerType) {
		this(Kingdom.randomKingdom(), ownerType);
	}

	/**
	 * Just setup JavaFX part of the object.
	 */
	private void setUI() {
		HBox content = new HBox();
		content.setAlignment(Pos.CENTER_LEFT);
		Label type = new Label(ownerType);
		type.setPrefWidth(25);
		Label nameL = new Label(" " + name);
		
		Rectangle rec = new Rectangle(30, 15, color);
		numberL = new Label(String.valueOf(castles.size()));
		numberL.setTextFill(color.getBrightness() < 0.5 ? Color.WHITE : Color.BLACK);
		StackPane castlesOwned = new StackPane();
		castlesOwned.getChildren().addAll(rec, numberL);

		content.getChildren().addAll(type, castlesOwned, nameL);
		crossed = new Line(0, 0, 100, 0);
		crossed.setStrokeWidth(3);
		crossed.setTranslateX(20);
		this.getChildren().addAll(content, crossed);
	}

	/**
	 * @return Owner color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set owner color
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return Owner name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set owner name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	/**
	 * @return List of all Castles owned by the Owner.
	 */
	public List<Castle> getCastles() {
		return castles;
	}

	/**
	 * Give a Castle to the Owner.
	 * 
	 * @param castle
	 */
	public void addCastle(Castle castle) {
		castles.add(castle);
		castle.setOwner(this);
		numberL.setText(String.valueOf(castles.size()));
		crossed.setVisible(castles.size() < 1);
	}

	/**
	 * To remove a Castle, the Owner will not owns this Castle.
	 * 
	 * @param castle
	 */
	public void removeCastle(Castle castle) {
		castles.remove(castle);
		numberL.setText(String.valueOf(castles.size()));
		crossed.setVisible(castles.size() < 1);
	}

	/**
	 * @return true if this Owner still have at least one Castle.
	 */
	public boolean isInGame() {
		return castles.size() > 0;
	}
	
	 private void writeObject(java.io.ObjectOutputStream out) throws IOException{
		 //the javafx.Color is not serializable and thus needs to be passed as a HSB array
		 colorHSB = new double[] {color.getHue(),color.getSaturation(), color.getBrightness()};
		 out.defaultWriteObject();
	 }
	 
	 private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
		 in.defaultReadObject();
		 color = Color.hsb(colorHSB[0], colorHSB[1], colorHSB[2]);
	 }
}

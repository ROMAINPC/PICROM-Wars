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

	public Owner(Kingdom kingdom, String ownerType) {
		this(kingdom.getColor(), kingdom.getName(), ownerType);
	}

	public Owner(Color color, String name, String ownerType) {
		this.color = color;
		this.name = name;
		this.ownerType = ownerType;
		castles = new LinkedList<Castle>();
		setUI();
	}

	public Owner(String ownerType) {
		this(Kingdom.randomKingdom(), ownerType);
	}
	
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
	
	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public List<Castle> getCastles() {
		return castles;
	}

	public void addCastle(Castle castle) {
		castles.add(castle);
		castle.setOwner(this);
		numberL.setText(String.valueOf(castles.size()));
		crossed.setVisible(castles.size() < 1);
	}

	public void removeCastle(Castle castle) {
		castles.remove(castle);
		numberL.setText(String.valueOf(castles.size()));
		crossed.setVisible(castles.size() < 1);
	}

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

package picrom.entity;

import java.util.LinkedList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import picrom.entity.castle.Castle;
import picrom.utils.Kingdom;
import picrom.utils.Utils.OwnerType;

public class Owner extends StackPane {

	private Color color;
	private String name; // not use as ID, prefer object reference
	private OwnerType ownerType;
	private List<Castle> castles;

	private Label numberL;
	private Line crossed;

	public Owner(Kingdom kingdom, OwnerType ownerType) {
		this(kingdom.getColor(), kingdom.getName(), ownerType);
	}

	public Owner(Color color, String name, OwnerType ownerType) {
		this.color = color;
		this.name = name;
		this.ownerType = ownerType;
		castles = new LinkedList<Castle>();
		setUI();
	}

	public Owner(OwnerType ownerType) {
		this(Kingdom.randomKingdom(), ownerType);
	}

	private void setUI() {
		HBox content = new HBox();
		content.setAlignment(Pos.CENTER_LEFT);
		Label type = new Label(ownerType.toString());
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

	public int getCastlesNumber() {
		return castles.size();
	}

	public void addCastle(Castle castle) {
		castles.add(castle);
		numberL.setText(String.valueOf(castles.size()));
		crossed.setVisible(castles.size() < 1);
	}

	public OwnerType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(OwnerType ownerType) {
		this.ownerType = ownerType;
	}

}

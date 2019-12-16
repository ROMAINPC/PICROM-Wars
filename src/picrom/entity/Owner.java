package picrom.entity;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import picrom.utils.Kingdom;
import picrom.utils.Utils.OwnerType;

public class Owner extends StackPane {

	private Color color;
	private String name; // not use as ID, prefer object reference
	private OwnerType ownerType;
	private int castlesNumber;

	private Label numberL;
	private Line crossed;

	public Owner(Color color, String name, OwnerType ownerType) {
		this.color = color;
		this.name = name;
		this.ownerType = ownerType;
		setUI();
		setCastlesNumber(1);
	}

	public Owner(OwnerType ownerType) {
		Kingdom k = Kingdom.randomKingdom();
		this.color = k.getColor();
		this.name = k.getName();
		this.ownerType = ownerType;
		setUI();
		setCastlesNumber(1);
	}

	private void setUI() {
		HBox content = new HBox();
		content.setAlignment(Pos.CENTER_LEFT);
		Label type = new Label(ownerType.toString());
		type.setPrefWidth(25);
		Label nameL = new Label(" " + name);

		Rectangle rec = new Rectangle(30, 12, color);
		numberL = new Label();
		numberL.setTextFill(color.getBrightness() < 0.5 ? Color.WHITE : Color.BLACK);
		StackPane castlesOwned = new StackPane();
		castlesOwned.getChildren().addAll(rec, numberL);

		content.getChildren().addAll(type, castlesOwned, nameL);
		crossed = new Line(0, 0, 100, 0);
		crossed.setStrokeWidth(3);
		crossed.setTranslateX(20);
		crossed.setVisible(false);
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
		return castlesNumber;
	}

	public void setCastlesNumber(int n) {
		this.castlesNumber = n;
		numberL.setText(String.valueOf(n));
		crossed.setVisible(n < 1);
	}

	public OwnerType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(OwnerType ownerType) {
		this.ownerType = ownerType;
	}

}

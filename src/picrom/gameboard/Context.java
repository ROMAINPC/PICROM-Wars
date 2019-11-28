package picrom.gameboard;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;

/**
 * Class representing a Group with fixed dimensions.
 */
public class Context extends Group {
	private SimpleDoubleProperty X;
	private SimpleDoubleProperty Y;
	private SimpleDoubleProperty width;
	private SimpleDoubleProperty height;

	public Context(double x, double y, double width, double height) {
		X = new SimpleDoubleProperty(x);
		Y = new SimpleDoubleProperty(y);
		this.width = new SimpleDoubleProperty(width);
		this.height = new SimpleDoubleProperty(height);
	}

	public Context(ReadOnlyDoubleProperty x, ReadOnlyDoubleProperty y, ReadOnlyDoubleProperty width,
			ReadOnlyDoubleProperty height) {
		X = new SimpleDoubleProperty();
		X.bind(x);
		Y = new SimpleDoubleProperty();
		Y.bind(y);
		this.width = new SimpleDoubleProperty();
		this.width.bind(width);
		this.height = new SimpleDoubleProperty();
		this.height.bind(height);
	}

	public SimpleDoubleProperty xProperty() {
		return X;
	}

	public SimpleDoubleProperty yProperty() {
		return Y;
	}

	public SimpleDoubleProperty widthProperty() {
		return width;
	}

	public SimpleDoubleProperty heightProperty() {
		return height;
	}

}

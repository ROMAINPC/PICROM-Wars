package picrom.gameboard;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
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

	/**
	 * Context with preserved ratio, context property may be different from property
	 * passed, however there is always a binding.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param ratio  Wanted preserved ratio, Context surface will cover the maximum
	 *               area but whithout affect this ratio. Ratio = Width / Height
	 */
	public Context(ReadOnlyDoubleProperty x, ReadOnlyDoubleProperty y, ReadOnlyDoubleProperty width,
			ReadOnlyDoubleProperty height, double ratio) {

		X = new SimpleDoubleProperty();
		Y = new SimpleDoubleProperty();
		this.width = new SimpleDoubleProperty();
		this.height = new SimpleDoubleProperty();

		When condition = Bindings.when((width.divide(height)).greaterThanOrEqualTo(ratio));
		this.width.bind(condition.then(this.height.multiply(ratio)).otherwise(width));
		this.height.bind(condition.then(height).otherwise(this.width.divide(ratio)));
		X.bind(x.add((width.subtract(this.width)).divide(2)));
		Y.bind(y.add((height.subtract(this.height)).divide(2)));
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

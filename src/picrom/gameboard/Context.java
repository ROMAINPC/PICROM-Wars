package picrom.gameboard;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * Class representing a Group with fixed dimensions.
 */
public class Context extends Group {
	private SimpleDoubleProperty X;
	private SimpleDoubleProperty Y;
	private SimpleDoubleProperty width;
	private SimpleDoubleProperty height;
	private SimpleDoubleProperty largerWidth;
	private SimpleDoubleProperty largerHeight;

	public Context() {
		X = new SimpleDoubleProperty();
		Y = new SimpleDoubleProperty();
		this.width = new SimpleDoubleProperty();
		this.height = new SimpleDoubleProperty();
	}

	public Context(double x, double y, double width, double height) {
		this();
		X.set(x);
		Y.set(y);
		this.width.set(width);
		this.height.set(height);
	}

	public Context(ReadOnlyDoubleProperty x, ReadOnlyDoubleProperty y, ReadOnlyDoubleProperty width,
			ReadOnlyDoubleProperty height) {
		this();
		X.bind(x);
		Y.bind(y);
		this.width.bind(width);
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
		this();
		When condition = Bindings.when((width.divide(height)).greaterThanOrEqualTo(ratio));
		this.width.bind(condition.then(this.height.multiply(ratio)).otherwise(width));
		this.height.bind(condition.then(height).otherwise(this.width.divide(ratio)));
		X.bind(x.add((width.subtract(this.width)).divide(2)));
		Y.bind(y.add((height.subtract(this.height)).divide(2)));
		largerWidth = new SimpleDoubleProperty();
		largerWidth.bind(width);
		largerHeight = new SimpleDoubleProperty();
		largerHeight.bind(height);
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

	public SimpleDoubleProperty largerWidthProperty() {
		return largerWidth;
	}

	public SimpleDoubleProperty largerHeightProperty() {
		return largerHeight;
	}

	/**
	 * Function to anchor a shape in the context.
	 * 
	 * @param s           The shape to anchor
	 * @param xRatio      X position in the context (value between 0.0 and 1.0)
	 * @param yRatio      Y position in the context (value between 0.0 and 1.0)
	 * @param widthRatio  Fraction of the Width of the context (value between 0.0
	 *                    and 1.0)
	 * @param heightRatio Fraction of the height of the context (value between 0.0
	 *                    and 1.0)
	 */
	public void bindIn(Rectangle s, double xRatio, double yRatio, double widthRatio, double heightRatio) {
		s.xProperty().bind(X.add(width.multiply(xRatio)));
		s.yProperty().bind(Y.add(height.multiply(yRatio)));
		s.widthProperty().bind(width.multiply(widthRatio));
		s.heightProperty().bind(height.multiply(heightRatio));
	}

	/**
	 * Function to anchor a ImageView in the context.
	 * 
	 * @param iV          The ImageView to anchor
	 * @param xRatio      X position in the context (value between 0.0 and 1.0)
	 * @param yRatio      Y position in the context (value between 0.0 and 1.0)
	 * @param widthRatio  Fraction of the Width of the context (value between 0.0
	 *                    and 1.0)
	 * @param heightRatio Fraction of the height of the context (value between 0.0
	 *                    and 1.0)
	 */
	public void bindIn(ImageView iV, double xRatio, double yRatio, double widthRatio, double heightRatio) {
		iV.xProperty().bind(X.add(width.multiply(xRatio)));
		iV.yProperty().bind(Y.add(height.multiply(yRatio)));
		iV.fitWidthProperty().bind(width.multiply(widthRatio));
		iV.fitHeightProperty().bind(height.multiply(heightRatio));
	}

	/**
	 * Function to anchor a Region in the context.
	 * 
	 * @param r           The Region to anchor
	 * @param xRatio      X position in the context (value between 0.0 and 1.0)
	 * @param yRatio      Y position in the context (value between 0.0 and 1.0)
	 * @param widthRatio  Fraction of the Width of the context (value between 0.0
	 *                    and 1.0)
	 * @param heightRatio Fraction of the height of the context (value between 0.0
	 *                    and 1.0)
	 */
	public void bindIn(Region r, double xRatio, double yRatio, double widthRatio, double heightRatio) {
		r.layoutXProperty().bind(X.add(width.multiply(xRatio)));
		r.layoutYProperty().bind(Y.add(height.multiply(yRatio)));
		r.prefWidthProperty().bind(width.multiply(widthRatio));
		r.prefHeightProperty().bind(height.multiply(heightRatio));
		r.maxHeightProperty().bind(height.multiply(heightRatio));
		r.minHeightProperty().bind(height.multiply(heightRatio));
	}

	/**
	 * Function to anchor a Label in the context relative to the center of the
	 * label;
	 * 
	 * @param l      The Region to anchor
	 * @param xRatio X position in the context (value between 0.0 and 1.0)
	 * @param yRatio Y position in the context (value between 0.0 and 1.0)
	 */
	public void bindCenterIn(Label l, double xRatio, double yRatio) {
		l.layoutXProperty().bind(X.add(width.multiply(xRatio).subtract(l.widthProperty().divide(2))));
		l.layoutYProperty().bind(Y.add(height.multiply(yRatio).subtract(l.heightProperty().divide(2))));
	}
	
	/**
	 * Function to anchor a ImageView in the context relative to the center of it.
	 * 
	 * @param iV          The ImageView to anchor
	 * @param xRatio      X position in the context (value between 0.0 and 1.0)
	 * @param yRatio      Y position in the context (value between 0.0 and 1.0)
	 * @param widthRatio  Fraction of the Width of the context (value between 0.0
	 *                    and 1.0)
	 * @param heightRatio Fraction of the height of the context (value between 0.0
	 *                    and 1.0)
	 */
	public void bindCenterIn(ImageView iV, double xRatio, double yRatio, double widthRatio, double heightRatio) {
		iV.xProperty().bind(X.add(width.multiply(xRatio).subtract(iV.fitWidthProperty().divide(2))));
		iV.yProperty().bind(Y.add(height.multiply(yRatio).subtract(iV.fitHeightProperty().divide(2))));
		iV.fitWidthProperty().bind(width.multiply(widthRatio));
		iV.fitHeightProperty().bind(height.multiply(heightRatio));
	}

}

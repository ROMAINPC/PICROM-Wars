package picrom.entity;

import javafx.scene.Scene;
import picrom.Drawables;
import picrom.Settings;
import picrom.entity.unit.Unit;

public class Castle extends Entity {

	public Castle(int owner, int X, int Y, Scene context) {
		super(Drawables.castle, owner, X, Y, context);
	}

	private int level;
	private int treasure;

	void addUnit(Unit u) {

	}

	void rmUnit(Unit u) {

	}

	@Override
	protected void updateUI() {
		this.fitWidthProperty().bind(context.widthProperty().divide(Settings.WORLD_WIDTH));
		this.fitHeightProperty().bind(context.heightProperty().divide(Settings.WORLD_HEIGHT));
		this.layoutXProperty().bind(this.fitWidthProperty().multiply(this.getWorldX()));
		this.layoutYProperty().bind(this.fitHeightProperty().multiply(this.getWorldY()));
	}
}

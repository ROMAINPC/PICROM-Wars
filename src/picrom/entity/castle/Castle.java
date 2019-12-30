package picrom.entity.castle;

import javafx.scene.image.ImageView;
import picrom.entity.Entity;
import picrom.entity.Owner;
import picrom.entity.unit.Unit;
import picrom.gameboard.World;
import picrom.utils.Drawables;
import picrom.utils.Settings;
import picrom.utils.Utils.Direction;
import picrom.utils.Utils.OwnerType;

public class Castle extends Entity implements Producible {

	private ProductionUnit productionUnit;
	private int level;
	private int treasure;
	private Door door;
	private Courtyard court;
	private int nextLevelCost, nextLevelTime;
	private int income;

	private ImageView circled;

	public Castle(Owner owner, int X, int Y, Direction doorDir, World context) {
		super(Drawables.castle, owner, X, Y, context);
		level = 1;
		productionUnit = new ProductionUnit(this);
		this.door = new Door(doorDir, false);
		court = new Courtyard();
		nextLevelCost = 1000 * level;
		nextLevelTime = 100 + 50 * level;
		income = level * Settings.INCOME_MULTIPLIER;

		circled = new ImageView(Drawables.circled);
		setCircled(false);
		double imageRatio = circled.getImage().getHeight() / image.getImage().getHeight();
		circled.layoutXProperty()
				.bind(image.layoutXProperty().subtract(image.fitWidthProperty().multiply((imageRatio - 1) / 2)));
		circled.layoutYProperty()
				.bind(image.layoutYProperty().subtract(image.fitHeightProperty().multiply((imageRatio - 1) / 2)));
		circled.fitHeightProperty().bind(image.fitHeightProperty().multiply(imageRatio));
		circled.fitWidthProperty().bind(image.fitWidthProperty().multiply(imageRatio));
		this.getChildren().addAll(circled);
	}

	public Castle(OwnerType type, int x, int y, Direction doorDir, World context) {
		this(new Owner(type), x, y, doorDir, context);
	}

	public void addUnit(Unit u) {
		court.addUnit(u);
	}

	public void rmUnit(Unit u) {
		court.removeUnit(u);
	}

	public ProductionUnit getProductionUnit() {
		return productionUnit;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		income = level * Settings.INCOME_MULTIPLIER;
	}

	public int getTreasure() {
		return treasure;
	}

	public void setTreasure(int treasure) {
		this.treasure = treasure;
	}
	
	public int getIncome() {
		return income;
	}

	public Door getDoor() {
		return door;
	}

	public void produce(Castle castle) {
		level++;
		nextLevelCost = 1000 * level;
		nextLevelTime = 100 + 50 * level;
	}

	public int getProductionCost() {
		return nextLevelCost;
	}

	public int getProductionTime() {
		return nextLevelTime;
	}

	public Courtyard getCourtyard() {
		return court;
	}

	public Castle getObjective() {
		return court.getObjective();
	}

	public void setObjective(Castle objective) {
		court.setObjective(objective);
	}

	public void updateProduction() {
		productionUnit.update();
	}

	public void setProduction(Producible p) {
		productionUnit.setProduction(p);
	}
	public Producible getProduction() {
		return productionUnit.getProduction();
	}

	public void setCircled(boolean b) {
		circled.setVisible(b);
	}
}

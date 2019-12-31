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
	private Class<? extends Producible> productionType;

	private ImageView circled;

	public Castle(Owner owner, int X, int Y, Direction doorDir, World context) {
		super(Drawables.castle, owner, X, Y, context);
		level = 1;
		productionUnit = new ProductionUnit(this);
		this.door = new Door(doorDir, false);
		court = new Courtyard(this);
		nextLevelCost = 1000 * level;
		nextLevelTime = 100 + 50 * level;
		income = level * Settings.INCOME_MULTIPLIER;
		productionType = null;
		setTreasure(Settings.START_TREASURE);

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
		door.setOpen(true);
	}

	public void updateProduction() {
		if (productionType != null) {
			if (productionUnit.isProduced()) { // start or restart new production
				if (productionType == Castle.class) {
					productionUnit.setProduction(this);
				} else {
					try {
						productionUnit
								.setProduction(productionType.getDeclaredConstructor(Castle.class).newInstance(this));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		productionUnit.update();
		if (productionUnit.isProduced()) {
			productionType = (productionType == Castle.class ? null : productionType); // stop production if it was
																						// castle improvments
		}
	}

	public void setProduction(Class<? extends Producible> productionType) {
		this.productionType = productionType;
		this.productionUnit.stop();
	}

	public Class<? extends Producible> getProduction() {
		return productionType;
	}

	public String productionName() {
		if (productionType == null)
			return "Pas de Production";
		if (productionType == Castle.class)
			return "Fortifications";
		return ((Unit) productionUnit.getProduction()).getName();
	}

	public void setCircled(boolean b) {
		circled.setVisible(b);
	}

}

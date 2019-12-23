package picrom.entity.castle;

import picrom.entity.Entity;
import picrom.entity.Owner;
import picrom.entity.unit.Unit;
import picrom.gameboard.World;
import picrom.utils.Drawables;
import picrom.utils.Utils.Direction;
import picrom.utils.Utils.OwnerType;

public class Castle extends Entity implements Producible {

	private ProductionUnit productionUnit;
	private int level;
	private int treasure;
	private Door door;

	public Castle(Owner owner, int X, int Y, Direction doorDir, World context) {
		super(Drawables.castle, owner, X, Y, 0, 0, context);
		level = 1;
		setNextLevelCost(level);
		setNextLevelTime(level);
		productionUnit = new ProductionUnit(this);
		this.door = new Door(doorDir, false);
	}

	public Castle(OwnerType type, int x, int y, Direction doorDir, World context) {
		this(new Owner(type), x, y, doorDir, context);
	}

	public void addUnit(Unit u) {
	}

	public void rmUnit(Unit u) {

	}

	public ProductionUnit getProductionUnit() {
		return productionUnit;
	}

	public void setNextLevelCost(int currentLevel) {
		// TODO change prodCost (Entity)
	}

	public void setNextLevelTime(int currentLevel) {
		// TODO change prodtime(Entity)
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTreasure() {
		return treasure;
	}

	public void setTreasure(int treasure) {
		this.treasure = treasure;
	}

	public Door getDoor() {
		return door;
	}

	public void produce(Castle castle) {
		// TODO change level and update next costs
	}

	public void updateProduction() {
		productionUnit.update();
	}
}

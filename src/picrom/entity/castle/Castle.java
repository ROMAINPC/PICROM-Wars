package picrom.entity.castle;

import picrom.entity.Entity;
import picrom.entity.unit.Unit;
import picrom.gameboard.World;
import picrom.settings.Drawables;

public class Castle extends Entity implements Producible {

	private ProductionUnit productionUnit;
	private int level;
	private int treasure;

	public Castle(int owner, int X, int Y, World context) {
		super(Drawables.castle, owner, X, Y, 0, 0, context);
		level = 1;
		setNextLevelCost(level);
		setNextLevelTime(level);
		productionUnit = new ProductionUnit(this);

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

	public void produce(Castle castle) {
		// TODO change level and update next costs
	}

	public void updateProduction() {
		productionUnit.update();

	}
}

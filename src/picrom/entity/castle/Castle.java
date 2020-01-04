package picrom.entity.castle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import picrom.entity.Entity;
import picrom.entity.unit.Unit;
import picrom.gameboard.World;
import picrom.owner.Owner;
import picrom.utils.Drawables;
import picrom.utils.Settings;
import picrom.utils.Utils.Direction;

public class Castle extends Entity implements Producible {

	private ProductionUnit productionUnit;
	private int level;
	private int treasure;
	private Door door;
	private Courtyard court;
	private int nextLevelCost, nextLevelTime;
	private int income;
	private Class<? extends Producible> productionType;

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

	}

	public void enterUnit(Unit u) {
		court.addUnit(u);
		u.setWorldX(this.getWorldX());
		u.setWorldY(this.getWorldY());
		u.setOrigin(this);
		u.setObjective(null);
	}

	public void launchUnit(Unit u) {
		court.removeUnit(u);
		u.setObjective(court.getObjective());
		getContext().engageUnit(u);
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

	public boolean isGarrison() {
		return court.getUnits().isEmpty();
	}

	public void produce(Castle castle) {
		setLevel(getLevel() + 1);
		nextLevelCost = 1000 * level;
		nextLevelTime = 100 + 50 * level;
	}

	public int getProductionCost() {
		return nextLevelCost;
	}

	public int getProductionTime() {
		return nextLevelTime;
	}

	public Castle getObjective() {
		return court.getObjective();
	}

	public void setObjective(Castle objective) {
		court.setObjective(objective);
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

	/**
	 * Get the time remaining to finish production choosed.
	 * 
	 * @return Number of update of updateProduction() needed to finish production.
	 *         Also consider turns to have enough money.
	 */
	public int getProductionTimeLeft() {
		int leftTime = productionUnit.getTimeLeft();
		int leftMoney = (productionUnit.getCost() - treasure) / income;
		leftTime = leftTime < 0 ? 0 : leftTime;
		leftMoney = leftMoney < 0 ? 0 : leftMoney;
		return Math.max(leftTime, leftMoney);
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

	public void attackWith(Unit attacker, int damage) {
		court.assault(damage);
		attacker.setDamage(attacker.getDamage() - damage);
	}

	/**
	 * To know which units are launchable, have an objective to do and the door
	 * open.
	 * 
	 * @return List of units to launch toward the objectiv
	 */
	public List<Unit> getLaunchList() {
		List<Unit> l = new LinkedList<Unit>();
		if (court.getObjective() != null && door.isOpen())
			l.addAll(court.getReadyUnits());
		return l;
	}

	/*
	 * Get number of each kind of unit currently in the castle.
	 * 
	 * @return Map which associate to a class of units the number of units of this
	 * type in garrison.
	 */
	public Map<Class<? extends Unit>, Integer> getGarrisonQuantity() {
		Map<Class<? extends Unit>, Integer> map = new HashMap<Class<? extends Unit>, Integer>();
		for (Unit unit : court.getUnits()) {
			Integer n = map.get(unit.getClass());
			if (n == null)
				n = 0;
			n++;
			map.put(unit.getClass(), n);
		}
		return map;
	}

}

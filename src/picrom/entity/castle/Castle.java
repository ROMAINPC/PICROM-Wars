/*******************************************************************************
 * Copyright (C) 2019-2020 ROMAINPC
 * Copyright (C) 2019-2020 Picachoc
 * 
 * This file is part of PICROM-Wars
 * 
 * PICROM-Wars is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * PICROM-Wars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package picrom.entity.castle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
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

/**
 * Castles are the main objectiv of this game. Castles are town wich contain
 * garrisons of Units, but also doors and production centers.
 * 
 * Castles have a treasure that they enrich over time.
 * 
 * It's possible to improve the level of a Castle to enrich quicker, Castle
 * class is a Producible.
 * 
 * Castles are also JavaFX components and must be added in a World context.
 * 
 * @see picrom.entity.unit.Unit
 * @see picrom.entity.castle.Courtyard
 * @see picrom.entity.castle.Door
 * @see picrom.entity.castle.ProductionUnit
 * @see picrom.entity.castle.Producible
 */
public class Castle extends Entity implements Producible, Serializable {

	private static final long serialVersionUID = 1L;

	private ProductionUnit productionUnit;
	private int level;
	private int treasure;
	private Door door;
	private Courtyard court;
	private int nextLevelCost, nextLevelTime;
	private int income;
	private Class<? extends Producible> productionType;

	/**
	 * Construct a new Castle in a World gameboard.
	 * 
	 * @param owner   The Owner of the castle
	 * @param X       X coordinate of the Castle in the World coordinate system.
	 * @param Y       Y coordinate of the Castle in the World coordinate system.
	 * @param doorDir Orientation of the Door
	 * @param context World context.
	 */
	public Castle(Owner owner, int X, int Y, Direction doorDir, World context) {
		super(owner, X, Y, context);
		level = 1;
		productionUnit = new ProductionUnit(this);
		this.door = new Door(doorDir, false);
		setUI();

		court = new Courtyard();
		nextLevelCost = 1000 * level;
		nextLevelTime = 100 + 50 * level;
		income = level * Settings.INCOME_MULTIPLIER;
		productionType = null;
		setTreasure(Settings.START_TREASURE);

	}

	// Reinstanciate a castle. Work in progress.
	public Castle(Castle castle, World context) {
		this(castle.getOwner(), (int) castle.getWorldX(), (int) castle.getWorldY(), castle.getDoor().getDirection(),
				context);
	}

	/**
	 * Just setup JavaFX part of the object.
	 */
	private void setUI() {
		switch (door.getDirection()) {
		case East:
			image.setImage(Drawables.castle_e);
			break;
		case South:
			image.setImage(Drawables.castle_s);
			break;
		case West:
			image.setImage(Drawables.castle_w);
			break;
		default:
			break;
		}
	}

	/**
	 * To add an Unit to the garrison (For instance produced Unit or entered Unit)
	 * 
	 * @param u the Unit
	 */
	public void enterUnit(Unit u) {
		court.addUnit(u);
		u.setWorldX(this.getWorldX());
		u.setWorldY(this.getWorldY());
		u.setOrigin(this);
		u.setObjective(null);
	}

	/**
	 * To exit an Unit from the Castle.
	 * 
	 * @param u the Unit
	 */
	public void launchUnit(Unit u) {
		court.removeUnit(u);
		u.setObjective(court.getObjective());
		getContext().engageUnit(u);
	}

	/**
	 * @return Level of the Castle
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Set Level of the Castle, affect income.
	 * 
	 * @param level new level
	 */
	public void setLevel(int level) {
		this.level = level;
		income = level * Settings.INCOME_MULTIPLIER;
	}

	/**
	 * @return Amount of money in the Castle
	 */
	public int getTreasure() {
		return treasure;
	}

	/**
	 * Set Amount of money in the Castle
	 * 
	 * @param treasure new treasure
	 */
	public void setTreasure(int treasure) {
		this.treasure = treasure;
	}

	/**
	 * @return Income of money per turn for the Castle
	 */
	public int getIncome() {
		return income;
	}

	/**
	 * @return the only Door of the Castle
	 */
	public Door getDoor() {
		return door;
	}

	/**
	 * @return true if there is at least on Unit in the Castle
	 */
	public boolean isGarrison() {
		return court.getUnits().isEmpty();
	}

	/**
	 * Increase level of the Castle.
	 * 
	 * @param castle unused
	 */
	public void produce(Castle castle) {
		setLevel(getLevel() + 1);
		nextLevelCost = 1000 * level;
		nextLevelTime = 100 + 50 * level;
	}

	@Override
	public int getProductionCost() {
		return nextLevelCost;
	}

	@Override
	public int getProductionTime() {
		return nextLevelTime;
	}

	/**
	 * @return Castle target of the Castle
	 */
	public Castle getObjective() {
		return court.getObjective();
	}

	/**
	 * Set Castle target of the Castle
	 * 
	 * @param objective new objectiv
	 */
	public void setObjective(Castle objective) {
		court.setObjective(objective);
	}

	/**
	 * Start stop or advance current production, relaunch same production if that
	 * was an Unit.
	 */
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

	/**
	 * Set the type of class to produce. Can be null.
	 * 
	 * @param productionType (use ".class")
	 */
	public void setProduction(Class<? extends Producible> productionType) {
		this.productionType = productionType;
		this.productionUnit.stop();
	}

	/**
	 * @return Current type of production. Can be null.
	 */
	public Class<? extends Producible> getProduction() {
		return productionType;
	}

	/**
	 * Call this method to trigger an attack on the Castle. Will inflict damages on
	 * the garrison.
	 * 
	 * @param attacker The ennemy Unit which attack the Castle
	 * @param damage   The amount of damages that the Unit will inflict.
	 */
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

	/**
	 * Called when Castle is de-serialized.
	 * 
	 * @param in ObjectInputStream
	 * @throws IOException            If IO error.
	 * @throws ClassNotFoundException If serialization problem
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setUI();
	}

}

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
package picrom.entity.unit;

import java.io.Serializable;

import picrom.entity.Entity;
import picrom.entity.castle.Castle;
import picrom.entity.castle.Producible;

/**
 * Units are mainly military units. They can be produced in Castles and sended
 * on the battleground.
 * 
 * Once launched they have a Castle target.
 * 
 * An Unit is characterized by an amount of health-points, an amount of damage
 * it can inflict and a speed of movement.
 * 
 * @see picrom.entity.castle.ProductionUnit
 */
public class Unit extends Entity implements Producible, Serializable {

	private static final long serialVersionUID = 1L;

	private int speed, hp, damage;
	private String name;
	private int productionCost, productionTime;

	private Castle origin;
	private Castle objective;

	/**
	 * Create new Unit
	 * 
	 * @param name     String, the name of the type of Unit
	 * @param prodCost Money cost to produce it
	 * @param prodTime Number of turn needed to produce it
	 * @param speed    Speed of movment per turn
	 * @param hp       Health points
	 * @param damage   Damage potential
	 * @param origin   owner Castle
	 */
	public Unit(String name, int prodCost, int prodTime, int speed, int hp, int damage, Castle origin) {
		super(origin);
		this.speed = speed;
		this.hp = hp;
		this.damage = damage;
		this.name = name;
		this.productionCost = prodCost;
		this.productionTime = prodTime;
		objective = null;
	}

	/**
	 * @return Unit speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set unit speed
	 * 
	 * @param speed new speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return Unit health points
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * Set Unit health points
	 * 
	 * @param hp new amount of points
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * @return Unit damage potential
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Set Unit damage potential
	 * 
	 * @param damage new amount of damages
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * @return Name of the type of the Unit
	 */
	public String getName() {
		return name;
	}

	/**
	 * Add the produced Unit to the garrison of the following Castle.
	 * 
	 * @param c Castle
	 */
	public void produce(Castle c) {
		c.enterUnit(this);
	}

	@Override
	public int getProductionCost() {
		return productionCost;
	}

	@Override
	public int getProductionTime() {
		return productionTime;
	}

	/**
	 * @return Target Castle of the Unit.
	 */
	public Castle getObjective() {
		return objective;
	}

	/**
	 * Set target Castle of the Unit.
	 * 
	 * @param objective new objective
	 */
	public void setObjective(Castle objective) {
		this.objective = objective;
	}

	/**
	 * @return Last Castle were was the Unit.
	 */
	public Castle getOrigin() {
		return origin;
	}

	/**
	 * Set origin Castle of the Unit (last Castle were was the Unit to be exact)
	 * 
	 * @param origin new origin
	 */
	public void setOrigin(Castle origin) {
		this.origin = origin;
	}

	@Override
	public String toString() {
		return getClass() + " (" + getWorldX() + ", " + getWorldY() + "), Owner: " + getOwner() + ", (" + speed + ", "
				+ hp + ", " + damage + ")";
	}

}

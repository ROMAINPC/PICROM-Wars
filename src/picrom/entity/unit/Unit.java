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

import picrom.entity.Entity;
import picrom.entity.castle.Castle;
import picrom.entity.castle.Producible;
import picrom.utils.Drawables.EntityAssets;

public class Unit extends Entity implements Producible {

	private int speed, hp, damage;
	private String name;
	private int productionCost, productionTime;

	private Castle origin;
	private Castle objective;

	public Unit(EntityAssets img, String name, int prodCost, int prodTime, int speed, int hp, int damage,
			Castle origin) {
		super(img, origin);
		this.speed = speed;
		this.hp = hp;
		this.damage = damage;
		this.name = name;
		this.productionCost = prodCost;
		this.productionTime = prodTime;
		objective = null;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public String getName() {
		return name;
	}

	public void produce(Castle c) {
		c.enterUnit(this);
	}

	public int getProductionCost() {
		return productionCost;
	}

	public int getProductionTime() {
		return productionTime;
	}

	public Castle getObjective() {
		return objective;
	}

	public void setObjective(Castle objective) {
		this.objective = objective;
	}

	public Castle getOrigin() {
		return origin;
	}

	public void setOrigin(Castle origin) {
		this.origin = origin;
	}

}

package picrom.entity.unit;

import picrom.entity.Entity;
import picrom.entity.castle.Castle;
import picrom.entity.castle.Producible;
import picrom.utils.Drawables.EntityAssets;

public class Unit extends Entity implements Producible {

	private int speed, hp, damage;
	private int damageDone;
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
		damageDone = 0;
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
		c.addUnit(this);
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
	
	public int getDamageDone() {
		return damageDone;
	}

	public void setDamageDone(int damageDone) {
		this.damageDone = damageDone;
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

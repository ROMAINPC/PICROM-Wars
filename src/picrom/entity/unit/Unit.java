package picrom.entity.unit;

import picrom.entity.Entity;
import picrom.entity.castle.Castle;
import picrom.entity.castle.Producible;
import picrom.utils.Drawables.EntityAssets;

public class Unit extends Entity implements Producible {

	private int speed, hp, damage;
	private String name;

	public Unit(EntityAssets img, String name, int prodCost, int prodTime, int speed, int hp, int damage, Castle origin) {
		super(img, prodCost, prodTime, origin);
		this.speed = speed;
		this.hp = hp;
		this.damage = damage;
		this.name = name;
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

}

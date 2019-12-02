package picrom.entity.unit;

import javafx.scene.image.Image;
import picrom.entity.Entity;
import picrom.entity.castle.Castle;
import picrom.entity.castle.Producible;
import picrom.settings.Drawables.EntityAssets;

public class Unit extends Entity implements Producible {

	private int speed, hp, damage;

	public Unit(EntityAssets img, int prodCost, int prodTime, int speed, int hp, int damage, Castle origin) {
		super(img, prodCost, prodTime, origin);
		this.speed = speed;
		this.hp = hp;
		this.damage = damage;
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

	public void produce(Castle c) {
		c.addUnit(this);
	}

}

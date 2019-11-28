package picrom.entity.unit;

import picrom.entity.castle.Castle;
import picrom.entity.castle.Producible;

public class Unit implements Producible {

	private String name;
	private int prodCost, prodTime, speed, hp, damage;

	public Unit(String name, int prodCost, int prodTime, int speed, int hp, int damage) {
		this.name = name;
		this.prodCost = prodCost;
		this.prodTime = prodTime;
		this.speed = speed;
		this.hp = hp;
		this.damage = damage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProdCost() {
		return prodCost;
	}

	public void setProdCost(int prodCost) {
		this.prodCost = prodCost;
	}

	public int getProdTime() {
		return prodTime;
	}

	public void setProdTime(int prodTime) {
		this.prodTime = prodTime;
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

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNecessaryTurns() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void produce(Castle c) {
		c.addUnit(this);
	}

}

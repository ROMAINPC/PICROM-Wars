package picrom;

public enum UnitType {
	// name, prodCost, prodTime, speed, hp, damage
	PIKEMAN("Pikeman", 100, 5, 2, 1, 1), 
	KNIGHT("Knight", 500, 20, 6, 3, 5), 
	ONAGER("Onagre", 1000, 50, 1, 5, 10);

	private String name;
	private int prodCost, prodTime, speed, hp, damage;

	UnitType(String name, int prodCost, int prodTime, int speed, int hp, int damage) {
		this.name = name;
		this.prodCost = prodCost;
		this.prodTime = prodTime;
		this.speed = speed;
		this.hp = hp;
		this.damage = damage;
	}

	public String toString() {
		return name;
	}

	public int getProdCost() {
		return prodCost;
	}

	public int getProdTime() {
		return prodTime;
	}

	public int getSpeed() {
		return speed;
	}

	public int getHp() {
		return hp;
	}

	public int getDamage() {
		return damage;
	}
}

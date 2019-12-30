package picrom.entity.castle;

public class ProductionUnit {

	private Castle castle;

	private Producible currentProduction;

	private int timeLeft;

	public ProductionUnit(Castle castle) {
		this.castle = castle;
		currentProduction = null;
	}

	public void update() {
		if (currentProduction != null) {
			timeLeft--;
			if (timeLeft <= 0 && castle.getTreasure() >= currentProduction.getProductionCost()) {
				castle.setTreasure(castle.getTreasure() - currentProduction.getProductionCost()); // Apply cost
				currentProduction.produce(castle);
				setProduction(currentProduction); // start again same production
			}

		}
	}

	public void setProduction(Producible production) {
		currentProduction = production;
		if (production != null)
			timeLeft = production.getProductionTime();
	}

	public Producible getProduction() {
		return currentProduction;
	}

}

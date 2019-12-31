package picrom.entity.castle;

public class ProductionUnit {

	private Castle castle;

	private Producible currentProduction;
	private boolean produced;

	private int timeLeft;

	public ProductionUnit(Castle castle) {
		this.castle = castle;
		produced = true;
	}

	public void update() {
		if(currentProduction != null) {
			if (!produced) {
				timeLeft--;
				if (timeLeft <= 0 && castle.getTreasure() >= currentProduction.getProductionCost()) {
					castle.setTreasure(castle.getTreasure() - currentProduction.getProductionCost()); // Apply cost
					currentProduction.produce(castle);
					produced = true;
				}
	
			}
		}
	}

	public void setProduction(Producible production) {
		currentProduction = production;
		produced = false;
		if (production != null)
			timeLeft = production.getProductionTime();
	}

	public Producible getProduction() {
		return currentProduction;
	}

	public boolean isProduced() {
		return produced;
	}

	public void stop() {
		produced = true;
	}

}

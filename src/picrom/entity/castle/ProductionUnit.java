package picrom.entity.castle;

import picrom.utils.Settings;
import picrom.utils.Utils.OwnerType;

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
		if (!produced) {
			timeLeft--;
			if (timeLeft <= 0 && castle.getTreasure() >= currentProduction.getProductionCost()) {
				castle.setTreasure(castle.getTreasure() - currentProduction.getProductionCost()); // Apply cost
				currentProduction.produce(castle);
				produced = true;
			}

		}
	}

	public void setProduction(Producible production) {
		currentProduction = production;
		produced = false;
		if (production != null) {
			int multiplier = castle.getOwner().getOwnerType() == OwnerType.Baron
					? Settings.NEUTRAL_PRODUCTION_MULTIPLIER
					: 1;
			timeLeft = production.getProductionTime() * multiplier;
		}

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

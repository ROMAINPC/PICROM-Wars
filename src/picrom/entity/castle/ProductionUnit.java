package picrom.entity.castle;

public class ProductionUnit {

	private Castle castle;

	private Producible currentProduction;

	private int nbTurnProd;
	private int turnCount;

	public ProductionUnit(Castle castle) {
		this.castle = castle;
	}

	public void update() {
		if (currentProduction != null) {

			// TODO while unit not produce

			// if conditions reunies:
			// TODO manage castle money and start new production
			currentProduction.produce(castle);
		}
	}

	public void setProduction(Producible production) {
		currentProduction = production;
	}

}

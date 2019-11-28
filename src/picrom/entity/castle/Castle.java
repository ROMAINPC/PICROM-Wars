package picrom.entity.castle;

import picrom.entity.Entity;
import picrom.entity.unit.Unit;
import picrom.gameboard.World;
import picrom.settings.Drawables;

public class Castle extends Entity implements Producible{
	
	private ProductionUnit productionUnit;

	

	public Castle(int owner, int X, int Y, World context) {
		super(Drawables.castle, owner, X, Y, context);
		productionUnit = new ProductionUnit(this);
		
	}

	private int level;
	private int treasure;

	public void addUnit(Unit u) {
	}

	public void rmUnit(Unit u) {

	}
	public ProductionUnit getProductionUnit() {
		return productionUnit;
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
	public void produce(Castle castle) {
		// TODO Auto-generated method stub
	}

	public void updateProduction() {
		productionUnit.update();
		
	}
}

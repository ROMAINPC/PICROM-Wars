package picrom.entity.castle;

public interface Producible {

	public void produce(Castle castle);
	public int getProductionCost();
	public int getProductionTime();

}

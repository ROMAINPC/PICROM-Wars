package picrom.entity.castle;

public interface Producible {
	
	public int getCost();

	public int getNecessaryTurns();
	public void produce(Castle castle);

}

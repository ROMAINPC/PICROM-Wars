package picrom.entity.castle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import picrom.entity.unit.Unit;
import picrom.utils.Settings;
import picrom.utils.SlowestUnitComparator;

public class Courtyard {
	// TODO find a solution to avoid sorting the list at each addition / suppression
	private List<Unit> units;
	private Castle objective;

	public Courtyard(Castle castle) {
		objective = null;
		units = new LinkedList<Unit>();
	}

	public void addUnit(Unit u) {
		units.add(u);
		Collections.sort(units, new SlowestUnitComparator());
	}

	public void removeUnit(Unit u) {
		units.remove(u);
		Collections.sort(units, new SlowestUnitComparator());
	}

	/**
	 * Choose units wich are ready to exit the court.
	 * 
	 * @return List of max 3 units, the slowest in the court.
	 */
	public List<Unit> getReadyUnits() {

		List<Unit> l = new LinkedList<Unit>();
		for (int i = 0; i < 3; i++)
			if (i < units.size())
				l.add(units.get(i));

		return l;

	}

	public void assault(int damage) {
		Random r = Settings.SEED;
		int idx = r.nextInt(units.size());
		Unit u = units.get(idx);
		u.setHp(u.getHp() - damage);
		if (u.getHp() <= 0) {
			removeUnit(u);
		}
	}

	public List<Unit> getUnits() {
		return units;
	}

	public Castle getObjective() {
		return objective;
	}

	public void setObjective(Castle objective) {
		this.objective = objective;
	}

	public String toString() {
		String str = "";
		for (Unit u : units) {
			str += u.getName() + " ";
		}
		return str;
	}
}
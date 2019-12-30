package picrom.entity.castle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import picrom.entity.unit.Unit;
import picrom.utils.Settings;
import picrom.utils.SlowestUnitComparator;

public class Courtyard {
	private List<Unit> units;
	private Castle objective;

	public Courtyard() {
		objective = null;
		units = new LinkedList<Unit>();
	}

	public void addUnit(Unit u) {
		units.add(u);
		Collections.sort(units, new SlowestUnitComparator());
	}

	public void removeUnit(Unit u) {
		units.remove(u);
	}

	/**
	 * Removes the first unit of the sorted list, and returns it in order to add it
	 * to the world.
	 * 
	 * @return The first unit of the sorted list.
	 */
	public List<Unit> takeOutUnits() {
		if (!units.isEmpty() && (objective != null)) {
			int i = 0;
			List<Unit> l = new LinkedList<Unit>();
			while (!units.isEmpty() && i < Settings.MAX_UNITS_OUT_BY_TURN) {
				Unit u = units.get(0);
				units.remove(0);
				 u.setObjective(objective);
				l.add(u);
				i++;
			}
			return l;
		}
		return null;
	}

	public String toString() {
		String str = "";
		for (Unit u : units) {
			str += u.getName() + " ";
		}
		return str;
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
}
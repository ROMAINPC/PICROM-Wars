package picrom.entity.castle;

import java.util.ArrayList;
import picrom.utils.Settings;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import picrom.utils.SlowestUnitComparator;
import picrom.entity.unit.*;

public class Courtyard {
	private List<Unit> units;
	private Castle castle;
	
	public Courtyard(Castle castle) {
		this.castle = castle;
		units =  new LinkedList<Unit>();
	}
	
	public void addUnit(Unit u) {
		units.add(u);
		Collections.sort(units, new SlowestUnitComparator());
	}
	
	public void removeUnit(Unit u) {
		units.remove(u);
	}
	
	/**
	 * Removes the first unit of the sorted list, and returns it in order to add it to the world.
	 * @return The first unit of the sorted list.
	 */
	public List<Unit> takeOutUnits() {
		if(!units.isEmpty() && (castle.getObjective() != null)) {
			int i = 0;
			List<Unit> l = new LinkedList<Unit>();
			while(!units.isEmpty() && i < Settings.MAX_UNITS_OUT_BY_TURN) {
				Unit u = units.get(0);
				units.remove(0);
				l.add(u);
				i++;
			}
			return l;
		}
		return null;
	}
	
	public String toString() {
		String str = "";
		for(Unit u : units) {
			str += u.getName() + " ";
		}
		return str;
	}
	
	public List getUnits() {
		return units;
	}
}
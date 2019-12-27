package picrom.utils;

import java.util.Comparator;

import picrom.entity.unit.Unit;

public class SlowestUnitComparator implements Comparator<Unit> {
	
	@Override
	public int compare(Unit u1, Unit u2) {
		return ((Integer) u2.getSpeed()).compareTo(u1.getSpeed());
	}

}

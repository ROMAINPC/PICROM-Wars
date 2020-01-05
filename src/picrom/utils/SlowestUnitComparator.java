package picrom.utils;

import java.util.Comparator;

import picrom.entity.unit.Unit;

/**
 * Comparator to class Units by speed.
 */
public class SlowestUnitComparator implements Comparator<Unit> {

	public int compare(Unit u1, Unit u2) {
		return ((Integer) u1.getSpeed()).compareTo(u2.getSpeed());
	}

}

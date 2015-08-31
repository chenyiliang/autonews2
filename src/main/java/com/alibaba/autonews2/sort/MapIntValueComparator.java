package com.alibaba.autonews2.sort;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class MapIntValueComparator implements
		Comparator<Map.Entry<Object, Integer>> {

	@Override
	public int compare(Entry<Object, Integer> o1, Entry<Object, Integer> o2) {
		return o1.getValue().compareTo(o2.getValue());
	}

}

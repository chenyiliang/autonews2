package com.alibaba.autonews2.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SortUtil {
	public static LinkedHashMap<Object, Integer> sortMapByIntValue(
			Map<Object, Integer> map) {
		LinkedHashMap<Object, Integer> sortedMap = new LinkedHashMap<Object, Integer>();
		List<Map.Entry<Object, Integer>> entryList = new ArrayList<Map.Entry<Object, Integer>>(
				map.entrySet());
		Collections.sort(entryList, new MapIntValueComparator());
		Collections.reverse(entryList);
		for (int i = 0; i < entryList.size(); i++) {
			Entry<Object, Integer> entry = entryList.get(i);
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}

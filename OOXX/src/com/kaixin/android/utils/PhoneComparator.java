package com.kaixin.android.utils;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * User: alex
 * Date: 12-3-23
 * Time: ����7:20
 */
public class PhoneComparator implements Comparator<Map.Entry<String, List<Map<String, String>>>> {

    @Override
    public int compare(Map.Entry<String, List<Map<String, String>>> map, Map.Entry<String, List<Map<String, String>>> map1) {
    	return  map.getValue().size() - map1.getValue().size();
    }
}
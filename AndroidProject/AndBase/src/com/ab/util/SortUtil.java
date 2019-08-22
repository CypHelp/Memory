package com.ab.util;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 　　　　　　　 ┏┓　      ┏┓
 * 　　　　　　　┏┛┻━━━━━━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━      ┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...  ┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　   　┏━┛
 * 　　　　　    　┃　　   　┃　Code is far away from bug with the animal protecting
 * 　　　　　    　┃　　   　┃	             神兽保佑,代码无bug
 * 　　　　　    　┃　　   　┃
 * 　　　　　    　┃　　   　┃
 * 　　　　　    　┃　　   　┃
 * 　　　　　    　┃　　   　┃
 * 　　　　　    　┃　　   　┗━━━┓
 * 　　　　　    　┃　　　　　　　┣┓
 * 　　　　　    　┃　　　　　　　┏┛
 * 　　　　　    　┗┓┓┏━━━━━━┳┓┏┛
 * 　　　　　    　 ┃┫┫      ┃┫┫
 * 　　　　　    　 ┗┻┛    　┗┻┛
 * Created by paulxu on 15/11/30.
 */
public class SortUtil {
    /**
     * 按照key升序排列hashMap
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortByKeyASC(Map<String, String> map) {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.putAll(map);
        return sortedMap;
    }

    /**
     * 按照key降序排列hashMap
     *
     * @param map
     * @return
     */

    public static Map<String, String> sortByKeyDESC(Map<String, String> map) {
        SortedMap<String, String> sortedMap = new TreeMap<String, String>(new Comparator() {
            @Override
            public int compare(Object lhs, Object rhs) {
                return rhs.toString().compareTo(lhs.toString());
            }
        });
        sortedMap.putAll(map);
        return sortedMap;
    }

}

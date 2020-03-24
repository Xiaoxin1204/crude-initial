package com.longshare.microservice.demo.crude.utils;

import org.springframework.util.CollectionUtils;

import java.util.*;

public class SortUtils {
    /**
     * 把页面传过来的sort,order字符串以逗号为界进行分割
     */
    public static String formatOrderBy(List<String> sortList, List<String> orderList) {
        if (CollectionUtils.isEmpty(sortList)|| CollectionUtils.isEmpty(orderList)) {
            return null;
        } else {
            String orderBy = null;
            if (sortList.size() == orderList.size() && sortList.size() > 0) {
                for (int i = 0; i < orderList.size(); i++) {
                    if(SystemConstants.ORDER_ASC.equals(orderList.get(i)) || SystemConstants.ORDER_DESC.equals(orderList.get(i))){
                        String str = sortList.get(i) + " " + orderList.get(i) + ",";
                        if(orderBy != null){
                            orderBy = orderBy.concat(str);
                        }else {
                            orderBy = str;
                        }
                    }else {
                        return null;
                    }
                }
                orderBy = orderBy.substring(0, orderBy.length() - 1);
            }
            return orderBy;
        }
    }

    /**
     * 注释去掉后字段排序
     * @param sortList
     * @return
     */
    public static List<String> decompile(List<String> sortList){
        if(!CollectionUtils.isEmpty(sortList)){
            for(String sort:sortList){
                if(sort.contains("_text")){
                    sort.replace("_text","");
                }
            }
        }
        return sortList;
    }


    //Map降序排序
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDes(Map<K, V> map)
    {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}

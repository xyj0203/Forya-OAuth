package com.wojucai.util;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;


/**
 * JPA排序条件封装工具类
 */
public class SortUtil {

    /**
     * 基本的排序
     * @return
     */
    public static Sort basicSort() {
        return basicSort("DESC", "id");
    }


    /**
     * 单排序
     * @param orderType 排序的类型 ASC 正序 DESC 倒叙
     * @param orderFiled 排序的作用域
     * @return  合成后的排序方式
     */
    public static Sort basicSort(String orderType, String orderFiled) {
        Sort sort = Sort.by(Sort.Direction.fromString(orderType), orderFiled);
        return sort;
    }

    /**
     * 多列排序
     * @param asc 正序的字符串列
     * @param desc 逆序的字符串列
     * @return sort对象
     */
    public static Sort sortMultiColumn(String asc, String desc) {
        List<Sort.Order> orders = obtainOrder(asc, desc);
        if (orders.size() == 0) {
            return null;
        }
        return Sort.by(orders);
    }

    /**
     * 得到Order对象
     * @param asc 正序字符串
     * @param desc 逆序字符串
     * @return Order对象
     */
    private static List<Sort.Order> obtainOrder(String asc, String desc) {
        String[] splitAsc = null, splitDesc = null;
        if (asc != null) {
            splitAsc = asc.split(",");
        }
        if (desc != null) {
            splitDesc = desc.split(",");
        }
        List<Sort.Order> list = new ArrayList<>((splitAsc == null ? 0 : splitAsc.length) + (splitDesc == null ? 0 : splitDesc.length));
        for (int i = 0; i < (splitAsc == null ? 0 : splitAsc.length); i++) {
            list.add(Sort.Order.asc(splitAsc[i]));
        }
        for (int i = 0; i < (splitDesc == null ? 0 : splitDesc.length); i++) {
            list.add(Sort.Order.desc(splitDesc[i]));
        }
        return list;
    }



    /**
     * 驼峰命名转下划线
     * @param orderFiled 驼峰命名
     * @return 下划线命名
     */
    private static String HumpParseUnderline(String orderFiled) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < orderFiled.length(); i++) {
            char ch = orderFiled.charAt(i);
            if (Character.isUpperCase(ch)) {
                stringBuilder.append("_");
                stringBuilder.append(Character.toLowerCase(ch));
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }
}

package com.wojucai.util;

import org.springframework.data.domain.Sort;


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
     * 驼峰命名转下划线
     * @param orderFiled 驼峰命名
     * @return 下划线命名
     */
    private String HumpParseUnderline(String orderFiled) {
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

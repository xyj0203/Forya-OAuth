package com.wojucai.util;

import java.util.List;

/**
 * @description:参数校验类
 * @author: xuyujie
 * @date: 2023/06/11
 **/
public class ParamsVerify {

    /**
     * 校验字符串
     * @param str 待校验字符串
     * @return 校验结果
     */
    public static boolean verifyString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * 校验id
     * @param id 传入的参数
     * @return 返回的值
     */
    public static boolean verifyInteger(Integer id) {
       return !(id == null || id < 0);
    }

    /**
     * 校验list
     * @param list 传入的参数列表
     * @return 返回值
     */
    public static boolean verifyList(List list) {
        return !(list == null || list.size() == 0);
    }
}

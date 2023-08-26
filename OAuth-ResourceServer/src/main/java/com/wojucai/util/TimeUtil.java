package com.wojucai.util;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/24
 **/
public class TimeUtil {

    public static long start;

    public static void start() {
        // 记录请求开始时间
        start = System.nanoTime();
    }

    public static void end() {
        // 计算请求耗时
        long endTime = System.nanoTime();
        long duration = endTime - start;
        System.out.println(duration/ 1_000_000);
    }
}

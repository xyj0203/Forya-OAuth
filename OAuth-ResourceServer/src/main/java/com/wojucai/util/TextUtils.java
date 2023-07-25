package com.wojucai.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @description: 文本工具类
 * @author: xuyujie
 * @date: 2023/06/10
 **/
public abstract class TextUtils {
    private static final byte[] CODE_BASE
        = "abcdefghijklmnopqrstuvwxyz-ABCDEFGHIJKLMNOPQRSTUVWXYZ-0123456789".getBytes();
    private static final byte[] hexMap = {
            '0', '1', '2', '3',
            '4', '5', '6', '7',
            '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F',
    };

    public TextUtils() {
        throw new IllegalStateException();
    }

    /**
     * 生成随机字符串
     * @param length 长度
     * @return  随机字符串
     */
    public static String generateRandomCode(int length) {
        Random random = new SecureRandom();
        if (length < 1) {
            throw new IllegalArgumentException("length must a positive number");
        }
        byte[] codes = new byte[length];
        for (int i = 0; i < length; i++) {
            codes[i] = CODE_BASE[random.nextInt(CODE_BASE.length)];
        }
        return new String(codes);
    }

    /**
     * 字节数组转为十六进制格式
     * @param src 输入
     * @return 十六进制字符串
     */
    public static String bytesToHex(byte[] src) {
        byte[] strArr = new byte[src.length << 1];
        int t;
        int i = 0;
        for (byte b : src) {
            t = b & 0xff;
            strArr[i++] = hexMap[t >> 4];
            strArr[i++] = hexMap[t & 0xf];
        }
        return new String(strArr);
    }
}

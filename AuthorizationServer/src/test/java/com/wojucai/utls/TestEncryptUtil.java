package com.wojucai.utls;

import com.wojucai.utils.encrypt.EncryptUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/19
 **/
@SpringBootTest
public class TestEncryptUtil {
    @Autowired
    private EncryptUtil encryptUtil;

    @Test
    void test() {
        System.out.println(encryptUtil.encode("123456"));
    }
}

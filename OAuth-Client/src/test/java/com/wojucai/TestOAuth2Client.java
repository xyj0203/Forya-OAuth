package com.wojucai;

import org.junit.Test;

import java.lang.annotation.Target;
import java.net.URISyntaxException;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/19
 **/

public class TestOAuth2Client {

    @Test
    public void test() throws URISyntaxException {// 使用 Jackson
        OAuth2Client oAuth2Client = OAuth2Client.builder()
                .withClientId("gfU4-fXC-RNp6BiU")
                .withClientSecret("xTFANdTeVwNvhnjB")
                .withRedirectUri("http://localhost:8080").build();
        System.out.println(oAuth2Client.getLoginLink());
    }
}

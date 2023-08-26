package com.wojucai.util;

import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.wojucai.OAuth2Client;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/20
 **/
@SpringBootTest
public class TokenResolveTest {

    OAuth2Client oAuth2Client;

    @Test
    public void test() {
        OAuth2Client oAuth2Client = OAuth2Client.builder()
                .withClientId("C-BToSFjP67zCh9Q")
                .withClientSecret("XGQlEnhw5r5soDcx")
                .withRedirectUri("http://localhost:8080").build();
        String token = "eyJraWQiOiJkMDAzN2Y5NC0wN2Q3LTQxOWItODQ1Ny00ODAzNThjMmI0YzEiLCJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJzdWIiOiIyIiwiYXVkIjoiQy1CVG9TRmpQNjd6Q2g5USIsInNjcCI6WzU3OSw1NzUsNTc3LDU3NCw1ODFdLCJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODA4MSIsImV4cCI6MTY5MjYwNzU0OCwiaWF0IjoxNjkyNTIxMTQ4LCJqdGkiOiI2NWNjZTg2ZC0xNDczLTRiNjktOTUzMy1iOGQ1MGMzM2MzYzYifQ.otB1GK-tj3Z9ZUQ3sqxg1a-cSDH3OE0xqMjHoUheqpDYZd2Di0tw1AWxAP_b881TKF-OnwolNIQWKcTyPzQdTw";
        System.out.println(oAuth2Client.verifyToken(token));
    }
}

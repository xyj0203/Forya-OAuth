package com.wojucai.service;

import com.wojucai.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestClientService {

    @Autowired
    private ClientService clientService;



    @Test
    void testQueryClientScope() {
        Result result = clientService.queryScopeAll();
        System.out.println(result);
    }

//    @Test
//    void testClientScope() {
//        System.out.println(clientService.queryClientScope("C-BToSFjP67zCh9Q"));
//    }
}

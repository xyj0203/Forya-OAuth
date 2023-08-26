package com.wojucai.service.impl;

import com.wojucai.entity.po.Consent;
import com.wojucai.service.ConsentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/19
 **/
@SpringBootTest
public class ConsentServiceTest {

    @Autowired
    private ConsentService consentService;

    @Test
    void testSave() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(574);
        arrayList.add(575);
        arrayList.add(576);
        System.out.println(consentService.save(new Consent("C-BToSFjP67zCh9Q",2L , arrayList)));
    }
}

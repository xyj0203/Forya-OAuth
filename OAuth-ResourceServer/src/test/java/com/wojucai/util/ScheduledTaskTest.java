package com.wojucai.util;

import com.wojucai.util.scheduledTask.CacheScheduled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/23
 **/
@SpringBootTest
public class ScheduledTaskTest {

    @Autowired
    private CacheScheduled cacheScheduled;

    @Test
    void cacheTest() {
        cacheScheduled.performTask();
    }
}

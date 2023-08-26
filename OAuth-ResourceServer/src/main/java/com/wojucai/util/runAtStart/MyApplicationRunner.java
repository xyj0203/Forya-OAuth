package com.wojucai.util.runAtStart;

import com.wojucai.util.scheduledTask.CacheScheduled;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/24
 **/
@Component
public class MyApplicationRunner implements ApplicationRunner {


    private final CacheScheduled cacheScheduled;

    public MyApplicationRunner(CacheScheduled cacheScheduled) {
        this.cacheScheduled = cacheScheduled;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 初始化日期缓存
        cacheScheduled.performTask();
    }
}

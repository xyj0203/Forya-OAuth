package com.wojucai.util.scheduledTask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/23
 **/
@Component
@EnableScheduling
@Slf4j
public class CacheScheduled {

    private List<String> list;

    @Scheduled(cron = "0 0 1 * * ?") // 每天的 1:00:00 执行
    public void performTask() {
        list = new ArrayList<>(7);
        LocalDate now = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = now.minusDays(i);
            String format = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            list.add(format);
        }
        log.info("日期初始化完毕！");
    }

    public List<String> getList() {
        return list;
    }
}

package com.wojucai.service.impl;

import com.wojucai.Result;
import com.wojucai.service.IndexService;
import com.wojucai.util.scheduledTask.CacheScheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.wojucai.entity.codeEnum.CacheConstant.*;

/**
 * @description:主页操作
 * @author: xuyujie
 * @date: 2023/08/23
 **/
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private RedisTemplate<String,Integer> redisTemplate;
    @Autowired
    private CacheScheduled cacheScheduled;

    @Override
    public Result queryCount() {
        List<String> list = cacheScheduled.getList();
        List<Integer> count = new ArrayList<>(7);
        list.forEach(
                   date ->
                           count.add(queryCount(date))
        );
        Map map = new HashMap();
        map.put("date", list);
        map.put("count", count);
        return Result.success(map);
    }

    @Override
    public Result queryMethod() {
        Map<String,Integer> map = new LinkedHashMap<>();
        map.put(GET,queryMethod(GET));
        map.put(POST,queryMethod(POST));
        map.put(PUT,queryMethod(PUT));
        map.put(DELETE,queryMethod(DELETE));
        return Result.success(map);
    }

    private Integer queryMethod(String method) {
        Object count = redisTemplate.opsForValue().get(REQUEST_METHOD+method);
        if (count == null) {
            return 0;
        }
        return (Integer) count;
    }

    private Integer queryCount(String date) {
        Object count = redisTemplate.opsForValue().get(REQUEST_COUNT+date);
        if (count == null) {
            return 0;
        }
        return (Integer) count;
    }
}

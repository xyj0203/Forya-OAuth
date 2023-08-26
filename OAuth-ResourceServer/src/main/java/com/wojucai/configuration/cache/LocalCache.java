package com.wojucai.configuration.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wojucai.dao.RoleRepository;
import com.wojucai.entity.codeEnum.CacheConstant;
import com.wojucai.entity.po.Role;
import com.wojucai.util.scheduledTask.CacheScheduled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static com.wojucai.entity.codeEnum.CacheConstant.*;

/**
 * @description:本地缓存
 * @author: xuyujie
 * @date: 2023/08/24
 **/
@Configuration
@Slf4j
public class LocalCache {

    private final RedisTemplate redisTemplate;

    private final RoleRepository roleRepository;

    public LocalCache(RedisTemplate redisTemplate,
                      RoleRepository repository
                      ) {
        this.redisTemplate = redisTemplate;
        this.roleRepository = repository;
    }

    @Bean(name = "roleCache")
    public Cache<Integer,Role> roleCache() {
        List<Role> roles = roleRepository.findAll();
        redisTemplate.opsForHash().put(FAIL,ROLE,FALSE);
        roles.forEach(
                role -> {
                    redisTemplate.opsForHash().put(CacheConstant.ROLE_CACHE,
                            role.getRoleId().toString(),
                            role.getRoleName());
                }
        );
        log.info("REDIS ROLE INIT SUCCESS");
        return CacheBuilder.newBuilder()
                .build(new CacheLoader<Integer, Role>() {
                    @Override
                    public Role load(Integer integer) throws Exception {
                        return roleRepository.findById(integer).get();
                    }
                });
    }
}

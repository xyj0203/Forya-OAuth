package com.wojucai.service;

import com.wojucai.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "resource-server",
        url = "${feign.resource-server-url}"
)
public interface UserService {

    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
     Result findByUsername(String username);
}

package com.wojucai.service;

import com.wojucai.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "Index-Service",
        url = "${feign.resource-server-url}" + "${feign.sys-index-url}"
)
public interface IndexService {

    /**
     * 查询请求数量
     * @return
     */
    @GetMapping("/queryCount")
    Result queryCount();

    /**
     * 查询方法数量
     * @return
     */
    @GetMapping("/queryMethod")
    Result queryMethod();
}

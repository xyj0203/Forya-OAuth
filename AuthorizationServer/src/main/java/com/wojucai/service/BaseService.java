package com.wojucai.service;

import com.wojucai.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "Base-Service",
        url = "${feign.resource-server-url}"
)
public interface BaseService {

}

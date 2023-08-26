package com.wojucai.service;

import com.wojucai.Result;
import com.wojucai.entity.po.Consent;
import com.wojucai.entity.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "Consent-Service",
        url = "${feign.resource-server-url}"
)
public interface ConsentService {
    /**
     * 添加授权信息
     * @param consent 授权信息
     * @return
     */
    @PostMapping("/sysConsent/save")
    Result save(@RequestBody Consent consent);
}

package com.wojucai.service;

import com.wojucai.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "Role-Service",
        url = "${feign.resource-server-url}" + "${feign.sys-role-url}"
)
public interface RoleService {
    /**
     * Id查询角色
     * @param id
     * @return
     */
    @GetMapping("/query/{id}")
    Result queryRoleById(@PathVariable(value = "id")Integer id);

    @GetMapping("/queryAll")
    Result queryAll();
}

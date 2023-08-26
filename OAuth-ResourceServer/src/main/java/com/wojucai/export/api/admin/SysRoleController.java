package com.wojucai.export.api.admin;

import com.wojucai.Result;
import com.wojucai.entity.po.Role;
import com.wojucai.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:角色控制器
 * @author: xuyujie
 * @date: 2023/08/10
 **/
@Api(tags = "Controller-SysRole")
@RestController
@RequestMapping("/sysRole")
@Slf4j
public class SysRoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("查询角色")
    @GetMapping("query/{id}")
    public Result userRole(@PathVariable("id") @NotNull(message = "id不能为空") Integer id) {
        Role role = roleService.queryById(id);
        return Result.success(role);
    }

    @ApiOperation("获取所有角色")
    @GetMapping("/queryAll")
    @ResponseBody
    public Result queryAll() {
        List<Role> roles = roleService.queryAll();
        roles = roles == null ? new ArrayList<Role>() : roles;
        return Result.success(roles);
    }
}

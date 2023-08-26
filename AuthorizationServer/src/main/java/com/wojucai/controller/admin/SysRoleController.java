package com.wojucai.controller.admin;

import com.wojucai.Result;
import com.wojucai.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:角色系统功能
 * @author: xuyujie
 * @date: 2023/08/22
 **/
@RestController
@RequestMapping("/sysRole")
@Api(tags = "Controller-sysRole")
public class SysRoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("通过id查询角色")
    @GetMapping("/query/{id}")
    @ResponseBody
    public Result query(@PathVariable("id") Integer id) {
        return roleService.queryRoleById(id);
    }

    @ApiOperation("获取所有角色")
    @GetMapping("/queryAll")
    @ResponseBody
    public Result queryAll() {
        return roleService.queryAll();
    }


}

package com.wojucai.controller.admin;

import com.wojucai.Result;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.entity.validate.CheckId;
import com.wojucai.entity.validate.CheckString;
import com.wojucai.entity.validate.Update;
import com.wojucai.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Arrays;

/**
 * @description:客户端系统功能
 * @author: xuyujie
 * @date: 2023/08/22
 **/
@RestController
@RequestMapping("/sysClient")
@Api(tags = "Controller-SysClient")
public class SysClientController {

    @Autowired
    private ClientService clientService;

    @ApiOperation("通过客户端名称查询")
    @GetMapping("/queryClientByName")
    public Result queryClientByName(@Validated(value = CheckString.class) ClientQuery clientQuery) {
        return clientService.queryClientByName(clientQuery);
    }

    @ApiOperation("添加客户端")
    @PostMapping("/insertClient")
    public Result insertClient(@Validated @RequestBody Client client) {
        return clientService.insertClient(client);
    }

    @ApiOperation("更新客户端信息")
    @PutMapping("/updateClient")
    public Result updateClient(@Validated(value = {Update.class, Default.class}) @RequestBody Client client) {
        return clientService.updateClient(client);
    }

    @ApiOperation("通过Id删除客户端信息")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") @NotNull(message = "id不能为空") Integer id) {
        return clientService.deleteById(id);
    }

    @ApiOperation("批量删除客户端信息")
    @DeleteMapping("/deleteByIds")
    public Result deleteByIds(@Validated @NotNull(message = "客户端Id不能为空") @RequestBody Integer[] ids) {
        return  clientService.deleteByIds(Arrays.asList(ids));
    }

    @ApiOperation("通过id查询客户端")
    @GetMapping("/queryById")
    public Result queryById(@Validated(value = CheckId.class) ClientQuery clientQuery) {
        return  clientService.queryById(clientQuery);
    }

    @ApiOperation("查询所有客户端")
    @GetMapping("/queryAll")
    public Result queryAll(ClientQuery clientQuery) {
        return clientService.queryAll(clientQuery);
    }

    @ApiOperation("更新开启状态")
    @PutMapping("/changeEnable")
    public Result changeEnable(Integer id, Integer enable) {
        return clientService.changeEnable(id, enable);
    }

    @ApiOperation("/查询客户端作用域")
    @GetMapping("/queryScopeAll")
    public Result queryAll() {
        return clientService.queryScopeAll();
    }
}

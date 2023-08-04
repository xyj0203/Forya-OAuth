package com.wojucai.export.api;

import com.wojucai.Result;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.entity.validate.CheckId;
import com.wojucai.entity.validate.CheckString;
import com.wojucai.entity.validate.Update;
import com.wojucai.entity.vo.ClientVo;
import com.wojucai.enums.ResultEnum;
import com.wojucai.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 客户端控制类
 * @author: xuyujie
 * @date: 2023/06/11
 **/
@Api(tags = "Controller-Client")
@RestController
@RequestMapping("/client")
@Slf4j
public class ClientController {

    @Autowired
    private ClientService clientService;

    @ApiOperation("通过客户端名称查询")
    @GetMapping("/queryClientByName")
    public Result queryClientByName(@Validated(value = CheckString.class)  ClientQuery clientQuery) {
        Page<ClientVo> clients = clientService.queryByClientName(clientQuery);
        return clients != null ? Result.success(clients, ResultEnum.RequestSuccess) : Result.fail();
    }

    @ApiOperation("添加客户端")
    @PostMapping("/insertClient")
    public Result insertClient(@Validated @RequestBody Client client) {
        return clientService.insertClient(client) == null ?
                 Result.fail(ResultEnum.ParamsIllegal) : Result.success(ResultEnum.RequestSuccess);
    }

    @ApiOperation("更新客户端信息")
    @PutMapping("/updateClient")
    public Result updateClient(@Validated(value = {Update.class}) @RequestBody Client client) {
        return clientService.updateClient(client) != null ?
                Result.success(ResultEnum.RequestSuccess) : Result.fail(ResultEnum.RequestFail);
    }

    @ApiOperation("通过Id删除客户端信息")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable("id") @NotNull(message = "id不能为空") Integer id) {
        try {
            clientService.deleteById(id);
        } catch (Exception e) {
            throw new IllegalStateException("客户端删除失败！");
        }
        return Result.success(ResultEnum.RequestSuccess);
    }

    @ApiOperation("批量删除客户端信息")
    @DeleteMapping("deleteByIds")
    public Result deleteByIds(@Validated @NotNull(message = "客户端Id不能为空")@RequestBody List<Integer> ids) {
        try {
            clientService.batchDelete(ids);
        } catch (Exception e) {
            log.info("批量删除失败！！！");
            return Result.fail(ResultEnum.RequestFail);
        }
        return Result.success(ResultEnum.RequestSuccess);
    }

    @ApiOperation("通过id查询")
    @GetMapping("queryById")
    public Result queryById(@Validated(value = CheckId.class)  ClientQuery clientQuery) {
        ClientVo clientVo = clientService.queryById(clientQuery);
        return  clientVo != null ? Result.success(clientVo) : Result.fail("用户不存在");
    }

    @ApiOperation("查询所有")
    @GetMapping("queryAll")
    public Result queryAll(ClientQuery clientQuery) {
        Page<ClientVo> clientVos = clientService.queryAll(clientQuery);
        return clientVos != null ? Result.success(clientVos) : Result.fail("数据为空");
    }

    @ApiOperation("更新状态")
    @PutMapping("changeEnable")
    public Result changeEnable(@RequestParam("id") Integer id, @RequestParam("enable") Integer enable) {
        if (!(enable == 1 || enable == 0) || id == null) {
            return Result.fail(ResultEnum.ParamsIllegal);
        }
        Integer i = clientService.changeEnable(id, enable);
        return i == 1 ? Result.success(ResultEnum.RequestSuccess) : Result.fail("客户端不存在");
    }
}

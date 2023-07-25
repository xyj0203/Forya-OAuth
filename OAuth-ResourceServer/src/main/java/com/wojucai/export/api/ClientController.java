package com.wojucai.export.api;

import com.wojucai.Result;
import com.wojucai.entity.Client;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.enums.ResultEnum;
import com.wojucai.service.ClientService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wojucai.util.ParamsVerify.*;

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

    @GetMapping("/queryClientByName")
    public Result queryClientByName(ClientQuery clientQuery) {
        if (!verifyString(clientQuery.getClientName())) {
            return Result.fail(ResultEnum.ParamsIllegal);
        }
        return Result.success(clientService.queryByClientName(clientQuery.getClientName(),
                clientQuery.getPageNow(), clientQuery.getPageNumber()), ResultEnum.ParamsIllegal);
    }

    @PostMapping("/insertClient")
    public Result insertClient(@Validated Client client) {
//        if (!verifyClient(client)) {
//            return Result.fail(ResultEnum.ParamsIllegal);
//        }
        return clientService.insertClient(client) == null ?
                Result.success(ResultEnum.ParamsIllegal) : Result.fail(ResultEnum.ParamsIllegal);
    }

    @PostMapping("/updateClient")
    public Result updateClient(Client client) {
        if (client.getClientId() == null) {
            return Result.fail(ResultEnum.ParamsIllegal);
        }
        if (!verifyClient(client)) {
            return Result.fail(ResultEnum.ParamsIllegal);
        }
        return clientService.updateClient(client) == null ?
                Result.success(ResultEnum.RequestSuccess) : Result.fail(ResultEnum.RequestFail);
    }

    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        if (!verifyInteger(id)) {
            return Result.fail(ResultEnum.ParamsIllegal);
        }
        return clientService.deleteById(id) == 1 ?
                Result.success(ResultEnum.RequestSuccess) : Result.fail(ResultEnum.RequestFail);
    }

    @DeleteMapping("deleteByIds")
    public Result deleteByIds(List<Integer> ids) {
        if (!verifyList(ids)) {
            return Result.fail(ResultEnum.ParamsIllegal);
        }
        try {
            clientService.batchDelete(ids);
        } catch (Exception e) {
            log.info("批量删除失败！！！");
            return Result.fail(ResultEnum.RequestFail);
        }
        return Result.success(ResultEnum.RequestSuccess);
    }

    private boolean verifyClient(Client client) {
        if (client.getClientName() == null
                || !StringUtils.hasText(client.getClientName())) {
            return false;
        }
        if (client.getRedirectUrl() == null
                || !StringUtils.hasText(client.getRedirectUrl())) {
            return false;
        }
        if (client.getScope() == null
                || !StringUtils.hasText(client.getScope())) {
            return false;
        }
        if (client.getDescription() == null
                || !StringUtils.hasText(client.getDescription())) {
            return false;
        }
        if (client.getEnable() == null
                || !(client.getEnable() == 1 || client.getEnable() == 0)) {
            return false;
        }
        return true;
    }
}

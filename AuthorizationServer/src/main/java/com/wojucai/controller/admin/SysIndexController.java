package com.wojucai.controller.admin;

import com.wojucai.Result;
import com.wojucai.service.IndexService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:获取主页数据
 * @author: xuyujie
 * @date: 2023/08/23
 **/
@RestController
@RequestMapping("/sysIndex")
@Api(tags = "Controller-SysIndex")
public class SysIndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/queryCount")
    public Result queryCount() {
        return indexService.queryCount();
    }

    @GetMapping("/queryMethod")
    public Result queryMethod() {
        return indexService.queryMethod();
    }
}

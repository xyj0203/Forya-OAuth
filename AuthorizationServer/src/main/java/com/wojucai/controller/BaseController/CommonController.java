package com.wojucai.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/19
 **/
@Controller
@Api(tags = "Controller-Error")
public class CommonController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    @ApiOperation("404页面跳转登录")
    public String error() {
        return "/login";
    }

    /**
     * 映射未登录页面到vue主页
     * @return
     */
    @ApiOperation("映射用户登录页面")
    @GetMapping("/login")
    public String login() {
        return "index.html";
    }
}

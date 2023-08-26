package com.wojucai.controller.BaseController;

import com.wojucai.Result;
import com.wojucai.entity.OnlineState;
import com.wojucai.enums.ResultEnum;
import com.wojucai.service.OauthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @description:未登录用户的基础功能
 * @author: xuyujie
 * @date: 2023/08/22
 **/
@RestController
@RequestMapping("/baseUser")
@Api(tags = "Controller-BaseUser")
public class BaseUserController {

    @Autowired
    private OauthService oauthService;

    @ApiOperation("用户退出登录")
    @GetMapping("/userLogout")
    @ResponseBody
    public Result userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("loginState", null);
        return Result.success(ResultEnum.UserLogout);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    @ResponseBody
    public Result userLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpServletRequest httpServletRequest)  {
        return oauthService.userLogin(username, password,httpServletRequest);
    }

    @ApiOperation("获取用户登录信息")
    @GetMapping("/loginMessage")
    @ResponseBody
    public Result userLogin(
                            HttpServletRequest httpServletRequest)  {
        HttpSession session = httpServletRequest.getSession();
        OnlineState onlineState = (OnlineState) session.getAttribute("OnlineState");
        return onlineState == null ? Result.fail(ResultEnum.UserNotLogin) : Result.success(onlineState);
    }
}

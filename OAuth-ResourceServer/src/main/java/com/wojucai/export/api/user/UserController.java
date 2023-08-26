package com.wojucai.export.api.user;

import com.wojucai.Result;
import com.wojucai.entity.po.User;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.validate.CheckId;
import com.wojucai.entity.validate.CheckString;
import com.wojucai.entity.validate.Update;
import com.wojucai.entity.vo.ClientVo;
import com.wojucai.entity.vo.UserVo;
import com.wojucai.enums.ResultEnum;
import com.wojucai.service.UserService;
import com.wojucai.service.UserService;
import com.wojucai.util.TimeUtil;
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
 * 用户控制器
 */
@Api(tags = "Controller-User")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户登录")
    @GetMapping("/userLogin")
    public Result userLogin(String username) {
        if (username == null) {
            return Result.fail(ResultEnum.ParamsIllegal);
        }
        User user = userService.userLogin(username);
        return Result.success(user);
    }

    @ApiOperation("获取信息")
    @GetMapping("/userInfo")
    public UserVo userInfo() {
        return userService.userInfo();
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/updateInfo")
    public UserVo updateInfo(@RequestBody @Validated(value = {Update.class})User user) {
        return userService.updateInfo(user);
    }

}

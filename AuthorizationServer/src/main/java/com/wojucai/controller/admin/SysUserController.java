package com.wojucai.controller.admin;

import com.wojucai.Result;
import com.wojucai.entity.po.User;
import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.validate.CheckId;
import com.wojucai.entity.validate.CheckString;
import com.wojucai.entity.validate.Update;
import com.wojucai.entity.vo.UserVo;
import com.wojucai.enums.ResultEnum;
import com.wojucai.service.UserService;
import com.wojucai.utils.encrypt.EncryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

/**
 * @description:系统功能
 * @author: xuyujie
 * @date: 2023/08/22
 **/
@RestController
@RequestMapping("/sysUser")
@Api(tags = "Controller-SysUser")
public class SysUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EncryptUtil encryptUtil;

    @ApiOperation("通过用户账号查询")
    @GetMapping("/queryUserByName")
    public Result queryUserByName(@Validated(value = CheckString.class) UserQuery userQuery) {
        return userService.queryPageByName(userQuery);
    }

    @ApiOperation("添加用户")
    @PostMapping("/insertUser")
    public Result insertUser(@Validated(value = {Default.class})@RequestBody User user) {
        String password = user.getPassword();
        if (password.length() < 6 || password.length() > 15) {
            return Result.success(ResultEnum.PasswordNotValid);
        }
        user.setPassword(encryptUtil.encode(password));
        return userService.insertUser(user);
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/updateUser")
    public Result updateUser(@Validated(value = {Default.class,Update.class}) @RequestBody User user) {
        String password = user.getPassword();
        if (password != null) {
            if (password.length() < 6 || password.length() > 15) {
                return Result.success(ResultEnum.PasswordNotValid);
            }
            user.setPassword(encryptUtil.encode(password));
        }
        return userService.updateUser(user);
    }

    @ApiOperation("通过Id删除用户信息")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable("id") @NotNull(message = "id不能为空") Long id) {
        return userService.deleteById(id);
    }

    @ApiOperation("批量删除用户信息")
    @DeleteMapping("deleteByIds")
    public Result deleteByIds(@Validated @NotNull(message = "客户端Id不能为空") @RequestBody Long[] ids) {
        return userService.deleteByIds(Arrays.asList(ids));
    }

    @ApiOperation("通过id查询")
    @GetMapping("/queryById")
    public Result queryById(@Validated(value = CheckId.class)  UserQuery userQuery) {
       return userService.queryById(userQuery);
    }

    @ApiOperation("查询所有")
    @GetMapping("/queryAll")
    public Result queryAll(UserQuery userQuery) {
        return userService.queryAll(userQuery);
    }
}

package com.wojucai.export.api;

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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

    @ApiOperation("通过用户账号查询")
    @GetMapping("/queryUserByName")
    public Result queryUserByName(@Validated(value = CheckString.class) @RequestBody UserQuery userQuery) {
        Page<UserVo> Users = userService.queryByUsername(userQuery);
        return Users != null ? Result.success(Users, ResultEnum.RequestSuccess) : Result.fail(ResultEnum.UserNotExist);
    }

    @ApiOperation("添加用户")
    @PostMapping("/insertUser")
    public Result insertUser(@Validated @RequestBody User user) {
        return userService.insertUser(user) == null ?
                Result.fail(ResultEnum.ParamsIllegal) : Result.success(ResultEnum.RequestSuccess);
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/updateUser")
    public Result updateUser(@Validated(value = {Update.class}) @RequestBody User User) {
        return userService.updateUser(User) == null ?
                Result.success(ResultEnum.RequestSuccess) : Result.fail(ResultEnum.RequestFail);
    }

    @ApiOperation("通过Id删除用户信息")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable("id") @NotNull(message = "id不能为空") Long id) {
        try {
            userService.deleteById(id);
        } catch (Exception e) {
            throw new IllegalStateException("用户删除失败！");
        }
        return Result.success(ResultEnum.RequestSuccess);
    }

    @ApiOperation("批量删除用户信息")
    @DeleteMapping("deleteByIds")
    public Result deleteByIds(@Validated @NotNull(message = "客户端Id不能为空") List<Long> ids) {
        try {
            userService.batchDelete(ids);
        } catch (Exception e) {
            log.info("批量删除失败！！！");
            return Result.fail(ResultEnum.RequestFail);
        }
        return Result.success(ResultEnum.RequestSuccess);
    }

    @ApiOperation("通过id查询")
    @GetMapping("queryById/{id}")
    public Result queryById(@Validated(value = CheckId.class) @RequestBody UserQuery userQuery) {
        UserVo page = userService.queryById(userQuery);
        return  page != null ? Result.success(page) : Result.fail(ResultEnum.UserNotExist);
    }

    @ApiOperation("查询所有")
    @GetMapping("queryAll")
    public Result queryAll(UserQuery clientQuery) {
        Page<UserVo> userVos = userService.queryAll(clientQuery);
        return userVos != null ? Result.success(userVos) : Result.fail("数据为空");
    }

    @ApiOperation("通过用户账号查询")
    @GetMapping("/queryUserInfo")
    public Result queryUserInfo(@Validated(value = CheckString.class) @RequestBody UserQuery userQuery) {
        UserVo Users = userService.queryUserInfo(userQuery);
        return Users != null ? Result.success(Users, ResultEnum.RequestSuccess) : Result.fail(ResultEnum.UserNotExist);
    }
}

package com.wojucai.service;

import com.wojucai.Result;
import com.wojucai.entity.po.User;
import com.wojucai.entity.reqParam.UserQuery;
import feign.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@FeignClient(
        name = "User-Service",
        url = "${feign.resource-server-url}" + "${feign.sys-user-url}"
)
public interface UserService {

    /**
     * 通过用户名分页查询
     * @param userQuery 查询参数封装
     * @return
     */
    @GetMapping("/queryUserByName")
    Result queryPageByName(@SpringQueryMap UserQuery userQuery);

    /**
     * 添加用户
     * @param user 用户信息
     * @return
     */
    @PostMapping("/insertUser")
    Result insertUser(@RequestBody User user);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return
     */
    @PutMapping("/updateUser")
    Result updateUser(@RequestBody User user);

    /**
     * 通过id删除
     * @param id id信息
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    Result deleteById(@PathVariable("id") Long id);

    /**
     * 通过id批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteByIds")
    Result deleteByIds(List<Long> ids);

    /**
     * 通过Id查询
     * @param userQuery 查询参数封装
     * @return
     */
    @GetMapping("/queryById")
    Result queryById(@SpringQueryMap UserQuery userQuery);

    /**
     * 查询所有
     * @param userQuery 查询参数的封装
     * @return
     */
    @GetMapping("/queryAll")
    Result queryAll(@SpringQueryMap UserQuery userQuery);

    /**
     * 通过用户名查询单个用户信息
     * @param username 用户名
     * @return
     */
    @GetMapping("/queryUserInfo")
    Result findByUsername(@RequestParam("username") String username);

    /**
     * 用户登录
     * @param username 用户名
     * @return
     */
    @GetMapping("/userLogin")
    Result userLogin(@RequestParam("username") String username);
}

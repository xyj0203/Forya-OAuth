package com.wojucai.export.api.base;

import com.wojucai.Result;
import com.wojucai.entity.po.User;
import com.wojucai.enums.ResultEnum;
import com.wojucai.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/22
 **/
/**
 * 用户控制器
 */
@Api(tags = "Controller-BaseUser")
@RestController
@Slf4j
public class CommonController {
    @Autowired
    private UserService userService;

}

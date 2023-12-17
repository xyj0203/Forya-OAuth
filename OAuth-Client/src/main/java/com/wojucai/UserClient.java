package com.wojucai;

import com.wojucai.bean.interceptor.FeignRequestInterceptor;
import com.wojucai.bean.metadata.UserService;
import com.wojucai.entity.po.User;
import com.wojucai.entity.vo.UserVo;
import feign.Feign;
import org.springframework.util.Assert;

/**
 * @description:用户操作客户端
 * @author: xuyujie
 * @date: 2023/08/20
 **/
public class UserClient extends BaseClient{

    private String baseUrl = "http://localhost:8082/user";

    private UserService userService;

    public UserClient(String token) {
       this.userService = Feign.builder()
               .requestInterceptor(new FeignRequestInterceptor(token))
                .decoder(gsonDecoder)
               .encoder(gsonEncoder)
                .target(UserService.class, baseUrl);
    }

    /**
     * 获取用户授权信息
     * @return
     */
    public UserVo getUserInfo() {
        return userService.getUserInfo();
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public UserVo updateUserInfo(User user) {
        Assert.notNull(user.getUserId(), "userId can not be null");
        return userService.updateUserInfo(user);
    }
}

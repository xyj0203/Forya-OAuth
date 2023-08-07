package com.wojucai.service.impl;

import com.wojucai.entity.Result;
import com.wojucai.entity.vo.UserVo;
import com.wojucai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class OidcUserInfoServiceImpl {

    @Autowired
    private UserService userService;

    public OidcUserInfo loadUser(String username) {
        Result result = this.userService.findByUsername(username);
        if (result.getCode() == 10000) {
            UserVo userVo = (UserVo) result.getObject();
            OidcUserInfo.builder()
                    .subject(username)
                    .nickname(userVo.getNickName())
                    .preferredUsername(username)
                    .picture(userVo.getUserImage())
                    .birthdate(userVo.getBirthdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .locale("zh-cn")
                    .gender(userVo.getSex().toString())
                    .build();
        }
        throw new IllegalArgumentException("用户不存在");
    }

}

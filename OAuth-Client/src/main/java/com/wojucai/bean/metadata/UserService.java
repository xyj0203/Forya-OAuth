package com.wojucai.bean.metadata;

import com.wojucai.entity.po.User;
import com.wojucai.entity.vo.UserVo;
import feign.Headers;
import feign.RequestLine;

public interface UserService {

    @RequestLine("GET /userInfo")
    UserVo getUserInfo();

    @Headers("Content-Type: application/json")
    @RequestLine("PUT /updateInfo")
    UserVo updateUserInfo(User user);
}

package com.wojucai.bean.metadata;


import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.vo.UserVo;
import feign.QueryMap;
import feign.RequestLine;
import org.springframework.data.domain.Page;

public interface AdminService {

    /**
     * 查询所有
     * @param userQuery 查询参数的封装
     * @return
     */
    @RequestLine("GET /queryAll")
    Result<Page<UserVo>> queryAll(@QueryMap UserQuery userQuery);
}

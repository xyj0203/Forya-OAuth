package com.wojucai;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.google.gson.Gson;
import com.wojucai.bean.interceptor.FeignRequestInterceptor;
import com.wojucai.bean.metadata.AdminService;
import com.wojucai.bean.metadata.Result;
import com.wojucai.bean.metadata.UserService;
import com.wojucai.entity.codeEnum.ParamConstants;
import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.vo.UserVo;
import com.wojucai.json.GsonDecoder;
import feign.Feign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @description:管理员操作客户端
 * @author: xuyujie
 * @date: 2023/08/27
 **/
public class AdminClient extends BaseClient{

    private String baseUrl = "http://localhost:8082/sysUser";

    private AdminService adminService;

    public AdminClient(String token) {
        this.adminService = Feign.builder()
                .requestInterceptor(new FeignRequestInterceptor(token))
                .decoder(gsonDecoder)
                .encoder(gsonEncoder)
                .target(AdminService.class, baseUrl);
    }

    /**
     * 查询所有用户信息
     * @return
     */
    public Page<UserVo> queryAll() {
        return queryAll(ParamConstants.PAGE_NOW, ParamConstants.PAGE_NUMBER);
    }

    /**
     * 分页查询
     * @return
     */
    public Page<UserVo> queryAll(Integer pageNow, Integer pageNumber) {
        Assert.notNull(pageNow, "pageNow can not be null");
        Assert.notNull(pageNumber, "pageNumber can not be null");
        return queryAll(new UserQuery(pageNow, pageNumber,null,null));
    }

    /**
     * 分页查询
     * @return
     */
    public Page<UserVo> queryAll(String desc) {
        return queryAll(new UserQuery(ParamConstants.PAGE_NOW, ParamConstants.PAGE_NUMBER,null,desc));
    }

    private Page<UserVo> queryAll(UserQuery userQuery) {
        Result<Page<UserVo>> pageResult = adminService.queryAll(userQuery);
        return pageResult.getObject();
    }
}

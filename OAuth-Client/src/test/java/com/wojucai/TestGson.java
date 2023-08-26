package com.wojucai;

import com.google.gson.Gson;
import com.wojucai.entity.vo.UserVo;
import com.wojucai.json.GsonDecoder;
import org.junit.Test;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/22
 **/
public class TestGson {

    @Test
    public void testGson() {
        Gson gson = new Gson();
        Result result = gson.fromJson("{\"code\":10000,\"message\":\"请求成功\",\"object\":{\"content\":[{\"userId\":1,\"username\":\"admin\",\"nickName\":\"莴苣菜3号\",\"birthday\":\"2023-08-08 19:21:00\",\"userImage\":\"http://local\",\"sex\":\"男\",\"age\":18,\"description\":\"有点傻\",\"role\":{\"roleId\":2,\"roleDesc\":\"管理员\",\"roleName\":\"ADMIN\"}},{\"userId\":2,\"username\":\"userdemo\",\"nickName\":\"莴苣菜2号\",\"birthday\":\"2023-08-02 00:41:54\",\"userImage\":\"http://local\",\"sex\":\"男\",\"age\":18,\"description\":\"有点傻\",\"role\":{\"roleId\":1,\"roleDesc\":\"普通用户\",\"roleName\":\"USER\"}},{\"userId\":572,\"username\":\"user1\",\"nickName\":\"莴苣菜1号\",\"birthday\":\"2023-08-20 19:21:05\",\"sex\":\"女\",\"description\":\"我是普通人\",\"role\":{\"roleId\":1,\"roleDesc\":\"普通用户\",\"roleName\":\"USER\"}}],\"pageable\":{\"sort\":{\"empty\":true,\"sorted\":false,\"unsorted\":true},\"offset\":\"0\",\"pageSize\":10,\"pageNumber\":0,\"paged\":true,\"unpaged\":false},\"last\":true,\"totalPages\":1,\"totalElements\":\"3\",\"size\":10,\"number\":0,\"sort\":{\"empty\":true,\"sorted\":false,\"unsorted\":true},\"first\":true,\"numberOfElements\":3,\"empty\":false}}", Result.class);
        System.out.println(result);
    }
}

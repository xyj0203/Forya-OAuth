package com.wojucai;

import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.vo.UserVo;
import org.junit.Test;
import org.springframework.data.domain.Page;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/27
 **/
public class TestAdminClient {

    /**
     * 测试查询所有
     */
    @Test
    public void testQueryAll() {
        AdminClient adminClient = new AdminClient("eyJraWQiOiJkMDAzN2Y5NC0wN2Q3LTQxOWItODQ1Ny00ODAzNThjMmI0YzEiLCJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJzdWIiOiI3MzQiLCJhdWQiOiJnZlU0LWZYQy1STnA2QmlVIiwic2NwIjpbNzIxLDcyMCw3MjYsNzE3LDcxNiw3MTksNzE4LDcxNSw3MjUsNzI0LDcyMyw3MjJdLCJyb2xlIjoyLCJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODA4MSIsImV4cCI6MTY5MzE4MTgyMCwiaWF0IjoxNjkzMDk1NDIwLCJqdGkiOiJiNjhmNjRkNC1jZTU2LTRmYTEtOTBlNS02M2YzMWViZDcyM2IifQ.MCd1u9GJva3nayyYlTgIxECK788hILcUZnl5PbRNL1zyOMZFPAn40W-JLdenXHhG50ZXM01oMHnpoizNjtqmbA");
        Page<UserVo> page = adminClient.queryAll("birthday");
        System.out.println(page.getContent());
    }
}

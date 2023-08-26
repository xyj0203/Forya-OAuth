package com.wojucai;

import com.wojucai.entity.po.User;
import com.wojucai.entity.vo.UserVo;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/21
 **/
public class TestUserClient {
    @Test
    public void testUserInfo() throws URISyntaxException {// 使用 Jackson
        UserClient userClient =
                new UserClient("eyJraWQiOiJkMDAzN2Y5NC0wN2Q3LTQxOWItODQ1Ny00ODAzNThjMmI0YzEiLCJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJzdWIiOiI2NjYiLCJhdWQiOiJnZlU0LWZYQy1STnA2QmlVIiwic2NwIjpbNzIxLDcyMCw3MjYsNzE3LDcxNiw3MTksNzE4LDcxNSw3MjUsNzI0LDcyMyw3MjJdLCJyb2xlIjoxLCJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODA4MSIsImV4cCI6MTY5Mjk2OTYzOCwiaWF0IjoxNjkyODgzMjM4LCJqdGkiOiJlMGY1ZTE4Mi01NTBlLTQwZTUtOTJjMC0yYmMwMDVkN2M5MDkifQ.9yvbtUMYJR21g69exvpdOEx_JTKLQag80jLWeIKbLFv1YHbwGPGhpYsF-wJQbmdq1JHcHEMEhjZa9XcM6C0TcQ");
        User user = new User();
        user.setUserId(666L);
        user.setUsername("12334545");
        user.setNickName("我是测试的");
        userClient.updateUserInfo(user);
        UserVo userInfo = userClient.getUserInfo();
        System.out.println(userInfo);
    }

    @Test
    public void jiaoji() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(5);
        list1.add(2);
        list1.add(6);
        list1.add(4);
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(6);
        list2.add(4);
        System.out.println(list1);
        System.out.println(list2);
        list1.retainAll(list2);
        System.out.println(list1.size());
    }
}

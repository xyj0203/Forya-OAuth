# OAuth-Project

## 介绍
### 功能介绍
这是一款基于OAuth2授权的软件，与一般的OAuth2不同，这款软件主要是基于属性的读写权限进行控制。目前仅支持授权码模式。  
当添加客户端时可以选取我们需要的属性，然后生成指定的Client_Id和Client_Secret，然后交由客户端的SDK进行请求时使用。  
当用户进行登录时会进行校验所请求的客户端所授予的属性，然后进行授予权限，之后进行读写的时候指定获取和修改指定的属性。  
总的来说与一般的OAuth2授予权限不同的就是细粒度的划分更加的底层，能够控制属性的读写。
### 模块介绍
* AuthorizationServer 授权服务器
* OAuth-Client 客户端SDK
* OAuth-Common 共同依赖
* OAuth-ResourceServer 资源服务器
* oauth-vue 授权服务器前端
### 角色介绍
目前仅支持USER和ADMIN两种角色，之后可能会增加其他的动态角色。
## 软件架构
### AuthorizationServer
采用Spring-Web+SpringFox+Spring-validation+SpringCloud-OpenFeign+jose-jwt
### OAuth-Client
采用OpenFeign+Gson+jose-jwt+Junit
### OAuth-ResourceServer
采用Spring-Web+SpringFox+Spring-validation+Guava+Mysql+Redis+Jpa+jose-jwt
### oauth-vue
采用vue+vuex+element-ui+echarts+eslint+axios
##安装教程
授权服务器和资源服务器之间的通信认证是通过ip进行校验的，如果不在同一个主机下请进行自行修改。  
软件采取的是前后端不分离的方式，身份验证的方式是通过session进行验证的，所以前后端打包的文件需合并，将前端打包后的文件放入后端的resource下的static路径。
### 直接部署
SpringBoot项目修改application的配置文件配置
Vue项目修改指定的配置文件
+ 执行SQL文件
+ SpringBoot项目的命令打包
```cmd
mvn clean package
java -jar JAR包名
```
+ Vue项目命令打包
```cmd
npm install
npm run build
```

## 使用说明
### 账号
普通用户 normal normal
管理员 superAdmin superAdmin

### AuthorizationServer使用说明
#### 普通用户
1、登录界面
![登录界面](http://files.xyjxkl.top/img/login.png)
2、无授权的界面
![无授权界面](http://files.xyjxkl.top/img/nogrant.png "无授权界面")
3、有授权界面
![有授权界面](http://files.xyjxkl.top/img/hasgrant.png "无授权界面")
#### 管理员
1、登录界面
![登录界面](http://files.xyjxkl.top/img/login.png "登录")
2、主页
![主页](http://files.xyjxkl.top/img/index.png "主页")
3、用户管理
![用户管理](http://files.xyjxkl.top/img/user.png "用户管理")
4、客户端管理
![客户端管理](http://files.xyjxkl.top/img/client.png "客户端管理")
### OAuth-Client使用说明
```java
public class TestOAuth2Client {

    @Test
    public void test() throws URISyntaxException {// 使用 Jackson
        OAuth2Client oAuth2Client = OAuth2Client.builder()
                // ClientId
                .withClientId("gfU4-fXC-RNp6BiU")
                // ClientSecret
                .withClientSecret("xTFANdTeVwNvhnjB")
                // 重定向链接，主要接收返回的Code
                .withRedirectUri("http://localhost:8080").build();
        System.out.println(oAuth2Client.getLoginLink());
        
        // .....
        // 接收返回的Code
        String code = "...";
        // 然后通过code去请求授权服务器的token-endpoint然后获得AccessToken
        String access_token = "";
        // 然后可以用来获取用户的允许属性，也可以进行自己的拓展
    }

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
}
```
## 个人博客
http://blogs.xyjxkl.top
如果(❤ ω ❤)喜欢这个项目的话就多多支持吧，首次自己写一个全栈项目，BUG挺多，后续会慢慢更改，共同进步。


package com.wojucai.controller.user;


import cn.hutool.core.net.url.UrlBuilder;
import com.wojucai.Result;
import com.wojucai.entity.AccessTokenResponse;
import com.wojucai.entity.po.AuthorizationCode;
import com.wojucai.entity.OnlineState;
import com.wojucai.entity.po.Consent;
import com.wojucai.service.ClientService;
import com.wojucai.service.ConsentService;
import com.wojucai.service.OauthService;
import com.wojucai.service.RoleService;
import com.wojucai.service.impl.TokenService;
import com.wojucai.utils.text.TextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author xuyujie
 */
@Controller
@Api(tags = "Controller-Authorization")
@RequestMapping("/user")
public class AuthorizationController {
    private final ClientService clientService;
    private final OauthService oauthService;
    private final TokenService tokenService;
    private final ConsentService consentService;
    private final RoleService roleService;

    private final Map<String, String> concurrentReferenceHashMap = new HashMap<>();
    private final Map<String, String> uriHashMap = new HashMap<>();
    private final Map<String, AuthorizationCode> authCode = new HashMap<>();

    public AuthorizationController(
            ClientService clientService,
            OauthService oauthService,
            TokenService tokenService,
            ConsentService consentService, RoleService roleService) {
        this.clientService = clientService;
        this.oauthService = oauthService;
        this.tokenService = tokenService;
        this.consentService = consentService;
        this.roleService = roleService;
    }

    /**
     * 授权访问
     *
     * @param clientId 客户端索引
     * @param state    状态码
     * @return
     */
    @GetMapping(value = "/oauth2/authorize")
    @ApiOperation("通过客户端信息获取对应的code")
    public String authorize(
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("state") String state,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("response_type") String responseType,
            HttpServletRequest request) {
        if (!responseType.equals("code")) {
            throw new IllegalArgumentException("不支持的响应验证格式");
        }
        boolean checkClient = oauthService.checkClient(clientId, clientSecret);
        if (!checkClient) {
            throw new IllegalArgumentException();
        }
        // 把对应的给存储下
        concurrentReferenceHashMap.put(state, clientId);
        uriHashMap.put(state, redirectUri);
        // 将状态码设置到session中
        request.getSession().setAttribute("state", state);
        // 检查是否登录过了
        if (request.getSession().getAttribute("OnlineState") == null) {
            // 重定向到登录地址,把state带过去
            System.out.println(1111);
            return "redirect:/login";
        }
        System.out.println(111);
        // 登录过了, 直接走授权界面
        return "redirect:/login";
    }


    /**
     * 前端页面可以采用插槽的方式进行渲染，算了就让前端去渲染吧
     *
     * @return
     */
    @ApiOperation("查询客户端授权页面")
    @GetMapping(value = "/oauth/getClientScope")
    @ResponseBody
    public Result queryClientScope(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String state = (String) session.getAttribute("state");
        Assert.notNull(state, "state can not be null");
        String clientId = concurrentReferenceHashMap.get(state);
        Assert.notNull(clientId, "clientId can not be null");
        OnlineState onlineState = (OnlineState) session.getAttribute("OnlineState");
        return clientService.queryClientScope(clientId, onlineState.getUserId());
    }

    @ApiOperation("更新授权信息")
    @PostMapping("/oauth/submit")
    @ResponseBody
    public Result code(@RequestBody List<Integer> list, HttpServletRequest request) {
        Assert.notEmpty(list, "list can not be empty");
        System.out.println(list);
        HttpSession session = request.getSession();
        String state = (String) session.getAttribute("state");
        Assert.notNull(state, "state can not be null");
        session.setAttribute("state", null);
        String clientId = concurrentReferenceHashMap.remove(state);
        Assert.notNull(clientId, "clientId can not be null");
        OnlineState onlineState = (OnlineState) session.getAttribute("OnlineState");
        Assert.notNull(onlineState, "onlineState can not be null");
        System.out.println(state);
        System.out.println(clientId);
        Result save = consentService.save(new Consent(clientId, onlineState.getUserId(), list));
        System.out.println(save);
        if (save.getCode() != 10000) {
            return Result.fail("授权失败！");
        }
        String randomCode = TextUtils.generateRandomCode(10);
        AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setRole(onlineState.getRoleId());
        authorizationCode.setUserId(onlineState.getUserId());
        authorizationCode.setClientId(clientId);
        authorizationCode.setScope(list);
        authCode.put(randomCode, authorizationCode);
        String build = UrlBuilder.of(uriHashMap.remove(state))
                .addQuery("state", state)
                .addQuery("code", randomCode)
                .build();
        return Result.success(build);
    }

    /**
     * 校验是否登录
     *
     * @param code
     * @param grantType
     * @return
     */
    @ApiOperation("通过Code返回TokenResponse")
    @GetMapping("/oauth2/token")
	@ResponseBody
    public Result tokenResponse(
            @RequestParam("code") String code,
            @RequestParam("grant_type") String grantType) {
        if (!grantType.equals("authorization_code")) {
            throw new IllegalArgumentException("不支持的验证格式");
        }
        if (authCode.get(code) == null) {
            throw new IllegalArgumentException("参数不合法");
        }
        AccessTokenResponse tokenResponse = tokenService.generateToken(authCode.get(code));
        return Result.success(tokenResponse);
    }

    @ApiOperation("通过id查询角色")
    @GetMapping("/role/{id}")
    @ResponseBody
    public Result query(@PathVariable("id") Integer id) {
        return roleService.queryRoleById(id);
    }
}

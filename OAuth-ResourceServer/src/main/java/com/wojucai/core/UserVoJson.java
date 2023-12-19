package com.wojucai.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.wojucai.configuration.context.UserContext;
import com.wojucai.entity.Bo.Authorization;
import com.wojucai.entity.po.Property;
import com.wojucai.entity.vo.UserVo;
import com.wojucai.enums.RoleEnum;
import com.wojucai.service.ConsentService;
import com.wojucai.util.invoker.MethodInvoker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wojucai.entity.codeEnum.CacheConstant.SCOPE_CACHE;

@Slf4j
public class UserVoJson extends StdSerializer<UserVo> {

    private final RedisTemplate<String, Integer> redisTemplate;
    private final ConsentService consentService;
    @Qualifier("metaObjectMapper")
    @Autowired
    private  ObjectMapper objectMapper;

    public UserVoJson(
            RedisTemplate<String, Integer> redisTemplate,
            ConsentService consentService) {
        super(UserVo.class);
        this.redisTemplate = redisTemplate;
        this.consentService = consentService;
    }

    @Override
    public void serialize(UserVo userVo, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Authorization authorizationCode = UserContext.getAuthorizationCode();
        if (authorizationCode.getRole().equals(RoleEnum.ROLE_ADMIN.getRole())) {
            jsonGenerator.writeRawValue(objectMapper.writeValueAsString(userVo));
            return;
        }
        List<Integer> range = redisTemplate.opsForList().range(SCOPE_CACHE + "User", 0, -1);
        // 求交集
        range.retainAll(new ArrayList<>(authorizationCode.getScope()));
        // 查询出来的属性信息
        List<Property> scopeProperties = consentService.batchQueryProperty(range);
        UserVo copy = new UserVo();
        copy.setUserId(userVo.getUserId());
        scopeProperties.forEach(
                scopeProperty -> {
                    // 获取属性
                    MethodInvoker.invokeSetMethod(copy,
                            scopeProperty.getProperty(),
                            MethodInvoker.invokeGetMethod(userVo,scopeProperty.getProperty()));
                    // 获取属性
//                    try {
//                        MethodHandler.set(
//                                copy,
//                                scopeProperty.getProperty(),
//                                MethodHandler.get(userVo, scopeProperty.getProperty()));
//                    } catch (Throwable e) {
//                        throw new OAuthClientException("反序列化出错");
//                    }
                }
        );
        log.info(String.valueOf(userVo));
        jsonGenerator.writeRawValue(objectMapper.writeValueAsString(copy));
    }
}

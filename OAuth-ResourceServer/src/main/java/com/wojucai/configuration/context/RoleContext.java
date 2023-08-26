package com.wojucai.configuration.context;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/24
 **/
@Configuration
@ConfigurationProperties(prefix = "security")
public class RoleContext {
    private Map<String, List<String>> role;

    public Map<String, List<String>> getRole() {
        return role;
    }

    public void setRole(Map<String, List<String>> role) {
        this.role = role;
    }
}

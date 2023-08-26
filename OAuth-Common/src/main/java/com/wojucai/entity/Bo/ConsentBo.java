package com.wojucai.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

/**
 * 字段的业务类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsentBo {
    private Integer readId;
    private Integer writeId;
    private String propertyName;
}

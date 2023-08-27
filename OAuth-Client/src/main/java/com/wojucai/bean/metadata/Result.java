package com.wojucai.bean.metadata;

import com.wojucai.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result <T>{
    private Integer code;
    private String message;
    private T object;
}

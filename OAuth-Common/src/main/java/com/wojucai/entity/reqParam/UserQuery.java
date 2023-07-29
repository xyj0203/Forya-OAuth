package com.wojucai.entity.reqParam;

import com.wojucai.entity.validate.CheckId;
import com.wojucai.entity.validate.CheckString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户查询参数封装
 */
public class UserQuery extends PageQuery{


    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = {CheckId.class})
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = {CheckString.class})
    private String username;

    public UserQuery(Integer pageNow, Integer pageNumber, String sortAsc, String sortDesc) {
        super(pageNow, pageNumber, sortAsc, sortDesc);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

package com.wojucai.service;

import com.wojucai.Result;

public interface IndexService {

    /**
     * 查询请求数量
     * @return
     */
    Result queryCount();

    /**
     * 查询方法数量
     * @return
     */
    Result queryMethod();
}

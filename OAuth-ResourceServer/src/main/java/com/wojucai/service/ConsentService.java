package com.wojucai.service;

import com.wojucai.entity.po.Consent;
import com.wojucai.entity.po.Property;

import java.util.List;

public interface ConsentService {
    /**
     * 保存授权信息
     * @param consent
     * @return
     */
    Consent save(Consent consent);

    /**
     * 批量查询属性
     * @return
     */
    List<Property> batchQueryProperty(List<Integer> ids);
}

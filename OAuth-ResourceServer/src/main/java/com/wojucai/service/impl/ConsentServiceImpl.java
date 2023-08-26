package com.wojucai.service.impl;

import com.wojucai.dao.ConsentRepository;
import com.wojucai.dao.ScopePropertyRepository;
import com.wojucai.entity.po.Consent;
import com.wojucai.entity.po.ScopeProperty;
import com.wojucai.service.ConsentService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/19
 **/
@Service
public class ConsentServiceImpl implements ConsentService {

    @Resource
    private ConsentRepository consentRepository;

    @Resource
    private ScopePropertyRepository scopePropertyRepository;

    @Override
    public Consent save(Consent consent) {
        Consent save = consentRepository.save(consent);
        return save;
    }

    @Override
    public List<ScopeProperty> batchQueryProperty(List<Integer> ids) {
        List<ScopeProperty> allById = scopePropertyRepository.findAllById(ids);
        return allById == null ? new ArrayList<ScopeProperty>(0) : allById;
    }
}

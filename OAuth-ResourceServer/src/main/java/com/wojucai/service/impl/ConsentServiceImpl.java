package com.wojucai.service.impl;

import com.wojucai.dao.ConsentRepository;
import com.wojucai.dao.PropertyRepository;
import com.wojucai.entity.po.Consent;
import com.wojucai.entity.po.Property;
import com.wojucai.service.ConsentService;
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
    private PropertyRepository propertyRepository;

    @Override
    public Consent save(Consent consent) {
        Consent save = consentRepository.save(consent);
        return save;
    }

    @Override
    public List<Property> batchQueryProperty(List<Integer> ids) {
        List<Property> allById = propertyRepository.findAllById(ids);
        return allById == null ? new ArrayList<Property>(0) : allById;
    }
}

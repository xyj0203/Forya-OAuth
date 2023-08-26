package com.wojucai.export.api.admin;

import com.wojucai.Result;
import com.wojucai.entity.po.Consent;
import com.wojucai.service.ConsentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/19
 **/
@RestController
@Api(tags = "Controller-SysConsent")
@RequestMapping("/sysConsent")
public class SysConsentController {

    @Autowired
    private ConsentService consentService;

    @PostMapping("/save")
    public Result saveConsent(@RequestBody Consent consent) {
        System.out.println(consent);
        Consent save = consentService.save(consent);
        if (save == null) {
            return Result.fail();
        } else{
            return Result.success(save);
        }
    }
}

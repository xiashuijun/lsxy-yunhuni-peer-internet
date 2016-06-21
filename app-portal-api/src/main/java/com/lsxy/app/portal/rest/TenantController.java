package com.lsxy.app.portal.rest;

import com.lsxy.framework.tenant.model.Tenant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tandy on 2016/6/14.
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {

    @RequestMapping("/")
    public Tenant getTenantInfo(){
        return new Tenant();
    }
}

package com.lsxy.framework.config.dao;

import com.lsxy.framework.api.base.BaseDaoInterface;
import com.lsxy.framework.api.config.model.ApiGwBlankIP;

import java.io.Serializable;

/**
 * Created by Tandy on 2016/7/7.
 */
public interface ApiGwBlankIPDao extends BaseDaoInterface<ApiGwBlankIP, Serializable> {
    public ApiGwBlankIP findByIpAndStatus(String ip,int status);
}

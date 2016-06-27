package com.lsxy.framework.tenant.dao;



import com.lsxy.framework.core.persistence.BaseDaoInterface;
import com.lsxy.framework.tenant.model.Account;

import java.io.Serializable;

/**
 * Created by Tandy on 2016/6/24.
 */
public interface AccountDao extends BaseDaoInterface<Account, Serializable> {
    public Account findById(String id);
}
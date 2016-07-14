package com.lsxy.app.portal.rest.account;

import com.lsxy.app.portal.base.AbstractRestController;
import com.lsxy.framework.api.tenant.model.Account;
import com.lsxy.framework.api.tenant.service.AccountService;
import com.lsxy.framework.core.exceptions.MatchMutiEntitiesException;
import com.lsxy.framework.core.utils.PasswordUtil;
import com.lsxy.framework.web.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by zhangxb on 2016/6/28.
 * 实名认证
 */
@RequestMapping("/rest/account/safety")
@RestController
public class SafetyController extends AbstractRestController {
    @Autowired
    private AccountService accountService;

    /**
     * 验证密码是否正确
     * @param password 待验证密码
     * @return
     */
    @RequestMapping("/validation_password")
    public RestResponse validationPassword(String password) throws MatchMutiEntitiesException {
        String userName = getCurrentAccountUserName();
        Account account = accountService.findAccountByUserName(userName);
        if(password!=null&&password.length()>0){
            password =   PasswordUtil.springSecurityPasswordEncode(password,userName);
            if(password.equalsIgnoreCase(account.getPassword())){
                return RestResponse.success("1");
            }
        }
        return RestResponse.failed("1001","密码错误");
    }

    /**
     * 修改绑定手机号码
     * @param mobile 新绑定手机号码
     * @return
     */
    @RequestMapping("/save_mobile")
    public RestResponse saveMobile( String mobile) throws MatchMutiEntitiesException {
        String userName = getCurrentAccountUserName();
        Account account = accountService.findAccountByUserName(userName);
        account.setMobile(mobile);
        account = accountService.save(account);
        return RestResponse.success(account);
    }

    /**
     * 修改密码方法
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @RequestMapping("/save_password")
    public RestResponse savePassword(String oldPassword,String newPassword) throws MatchMutiEntitiesException {
        String userName = getCurrentAccountUserName();
        Account account = accountService.findAccountByUserName(userName);
        if(oldPassword!=null&&oldPassword.length()>0){
            // 密码加密
            oldPassword =  PasswordUtil.springSecurityPasswordEncode(oldPassword,userName);
            if(oldPassword.equalsIgnoreCase(account.getPassword())){
                account.setPassword(PasswordUtil.springSecurityPasswordEncode(newPassword,userName));
                account = accountService.save(account);
                if(account!=null){
                   return RestResponse.success("1");
                }else{
                    return RestResponse.failed("1002","修改数据库失败");
                }
            }else{
                return RestResponse.failed("1001","密码错误");
            }
        }else{
            return RestResponse.failed("1001","密码错误");
        }
    }
}
package com.lsxy.framework.api.events;

import com.lsxy.framework.mq.api.AbstractMQEvent;

/**
 * 重置密码邮箱验证成功的事的事件
 * Created by liups on 2016/7/27.
 */
public class ResetPwdVerifySuccessEvent extends AbstractMQEvent {

    private String email;//要重置密码的用户的邮箱

    @Override
    public String getTopicName() {
        return "topic_framework_account";
    }

    public ResetPwdVerifySuccessEvent() {
    }

    public ResetPwdVerifySuccessEvent(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
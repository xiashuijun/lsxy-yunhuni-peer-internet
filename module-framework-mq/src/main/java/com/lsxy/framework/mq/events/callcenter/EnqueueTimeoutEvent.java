package com.lsxy.framework.mq.events.callcenter;

import com.lsxy.framework.mq.api.AbstractDelayMQEvent;
import com.lsxy.framework.mq.topic.MQTopicConstants;

/**
 * Created by liuws on 2016/9/13.
 */
public class EnqueueTimeoutEvent extends AbstractDelayMQEvent{

    private String agentId;

    private String conditionId;

    private String queueId;

    private String type;

    private String tenantId;

    private String appId;

    private String callId;

    private String conversationId;

    public EnqueueTimeoutEvent(){}

    public EnqueueTimeoutEvent(String agentId,String conditionId, String queueId,String type, String tenantId, String appId, String callId,String conversationId, Integer delay){
        super(delay);
        this.agentId = agentId;
        this.conditionId = conditionId;
        this.queueId = queueId;
        this.type = type;
        this.tenantId = tenantId;
        this.appId = appId;
        this.callId = callId;
        this.conversationId = conversationId;
    }
    @Override
    public String getTopicName() {
        return MQTopicConstants.TOPIC_CALL_CENTER;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}

package com.lsxy.call.center.test;

import com.lsxy.call.center.api.model.*;
import com.lsxy.call.center.api.service.*;
import com.lsxy.call.center.states.state.AgentState;
import com.lsxy.call.center.states.state.ExtensionState;
import com.lsxy.framework.core.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Random;

/**
 * Created by liuws on 2016/11/17.
 */
@Component
@DependsOn("MQEventListener")
@Profile(value = {"test"})
public class InitTestData {

    @Autowired
    private ConditionService conditionService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private CallCenterAgentService callCenterAgentService;

    @Autowired
    private AgentSkillService agentSkillService;

    @Autowired
    private ExtensionState extensionState;

    @Autowired
    private AgentState agentState;

    @Autowired
    private AppExtensionService appExtensionService;

    @Autowired
    private EnQueueService enQueueService;

    @PostConstruct
    public void test() throws InterruptedException {
        Channel channel = null;
        if(channel == null){
            channel = new Channel();
            channel.setTenantId("8a2bc672576fb93d01576fbf04180000");
            channel.setAppId("8a2bc672576fb93d01576fc0fbd70003");
            channel.setRemark("通道1");
            channel = channelService.save(channel);
        }

        String skill_prefix = UUIDGenerator.uuid();
        for (int i = 0; i < 50; i++) {
            CallCenterAgent agent = new CallCenterAgent();
            agent.setTenantId(channel.getTenantId());
            agent.setAppId(channel.getAppId());
            agent.setChannel(channel.getId());
            agent.setNum(""+new Random(100000).nextInt());
            agent.setName("hehe"+new Random(100000).nextInt());
            agent = callCenterAgentService.save(agent);
            for (int j = 0; j < 10 ; j++) {
                AgentSkill skill = new AgentSkill();
                skill.setTenantId(channel.getTenantId());
                skill.setAppId(channel.getAppId());
                skill.setAgent(agent.getId());
                skill.setName(skill_prefix + j);
                skill.setScore(new Random().nextInt(100));
                skill.setEnabled(true);
                agentSkillService.save(skill);
            }
            AppExtension appExtension = new AppExtension();
            appExtension.setType(AppExtension.TYPE_TELPHONE);
            appExtension.setTenantId(channel.getTenantId());
            appExtension.setAppId(channel.getAppId());
            appExtension.setTelenum("13692206627");
            appExtension=appExtensionService.save(appExtension);
            String exid = appExtension.getId();
            extensionState.setAgent(exid,agent.getId());
            extensionState.setLastRegisterStatus(exid,200);
            extensionState.setLastRegisterTime(exid,new Date().getTime());
            extensionState.setRegisterExpires(exid,10000000);
            agentState.setExtension(agent.getId(),exid);
            agentState.setLastRegTime(agent.getId(),new Date().getTime());
            agentState.setLastTime(agent.getId(),new Date().getTime());
            agentState.setState(agent.getId(),AgentState.Model.STATE_IDLE);
        }
        Condition condition = new Condition();
        condition.setTenantId(channel.getTenantId());
        condition.setAppId(channel.getAppId());
        condition.setChannelId(channel.getId());
        condition.setWhereExpression("(get(\""+skill_prefix+"0\") + get(\""+skill_prefix+"1\")) > 60;");
        condition.setSortExpression("get(\""+skill_prefix+"0\") + get(\""+skill_prefix+"1\");");
        condition.setPriority(10);
        condition.setQueueTimeout(33);
        condition.setFetchTimeout(45);
        condition.setRemark("条件1");
        condition = conditionService.save(condition);

    }
}


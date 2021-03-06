package com.lsxy.call.center.test;

import com.lsxy.call.center.CallCenterMainClass;
import com.lsxy.call.center.api.service.CallCenterConversationService;
import com.lsxy.call.center.api.states.state.AgentState;
import com.lsxy.call.center.api.states.state.ExtensionState;
import com.lsxy.framework.cache.manager.RedisCacheService;
import com.lsxy.framework.config.Constants;
import com.lsxy.framework.core.exceptions.api.AppNotFoundException;
import com.lsxy.framework.core.exceptions.api.ExceptionContext;
import com.lsxy.framework.core.exceptions.api.ExtensionNotExistException;
import com.lsxy.framework.core.exceptions.api.YunhuniApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by liuws on 2016/11/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(CallCenterMainClass.class)
public class StateTest {

    private static final Logger logger = LoggerFactory.getLogger(StateTest.class);

    @Autowired
    private AgentState agentState;

    @Autowired
    private ExtensionState extensionState;

    @Autowired
    private CallCenterConversationService callCenterConversationService;

    static {
        //将 spring boot 的默认配置文件设置为系统配置文件
        System.setProperty("spring.config.location","classpath:"+ Constants.DEFAULT_CONFIG_FILE);
    }

    @Test
    public void test(){
        agentState.setLastRegTime("test",new Date().getTime());
        System.out.println(agentState.get("test").getLastRegTime());;

    }

    @Test
    public void test2() throws AppNotFoundException {
        try{
            throw new AppNotFoundException(new ExceptionContext().put("1111","呵呵"));
        }catch (YunhuniApiException e){
            logger.info("aaa",e);
            logger.error("bbb",e);
            logger.debug("ccc",e);
        }
    }

    @Test
    public void test3() throws YunhuniApiException {
        System.out.println(callCenterConversationService.detail("8a2bc5445a82e082015a8350ef51000f",null,"8a2a6a4a58b9c19d0158bd76a9310000","8fc76298afceb5c10a1200c4a8f0a1a6"));
    }
}

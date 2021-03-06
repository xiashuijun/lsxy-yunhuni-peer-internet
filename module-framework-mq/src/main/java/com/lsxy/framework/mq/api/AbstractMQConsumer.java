package com.lsxy.framework.mq.api;

import com.lsxy.framework.config.SystemConfig;
import com.lsxy.framework.core.utils.StringUtil;
import com.lsxy.framework.mq.exceptions.InvalidMQEventMessageException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


@SuppressWarnings("rawtypes")
public abstract class AbstractMQConsumer implements  MQConsumer{

	private static final Log logger = LogFactory.getLog(AbstractMQConsumer.class);
	@Autowired
	private MQHandlerFactory mqHandlerFactory;

	@Autowired
	private MQMessageParser mqMessageParser;

	//systemId 在 AbstractSpringBootStarter 中定义的 并有各自系统自己实现
	@Autowired
	private String systemId;

	public String[] getTopics(){
		if(StringUtil.isEmpty(systemId)){
			systemId = "global";
		}
		String t = SystemConfig.getProperty(systemId+".mq.subscribe.topics","yunhuni_topic_framework_tenant");
		String[] topics = null;
		if(StringUtil.isNotEmpty(t)){
			topics = t.split(",");
		}
		return topics;
	}
	
	/**
	 * 解析消息对象
	 * @param msg
	 * @return
	 */
	
	public MQEvent parseMessage(String msg)  throws InvalidMQEventMessageException {
		return mqMessageParser.parse(msg);
	}

	public MQHandlerFactory getMqHandlerFactory() {
		return mqHandlerFactory;
	}
}

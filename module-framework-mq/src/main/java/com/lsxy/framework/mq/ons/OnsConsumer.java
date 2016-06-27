package com.lsxy.framework.mq.ons;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.lsxy.framework.mq.MQEvent;
import com.lsxy.framework.mq.api.AbstractMQConsumer;
import com.lsxy.framework.mq.api.GlobalEventHandler;
import com.lsxy.framework.mq.api.GlobalEventHandlerFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.lsxy.framework.config.SystemConfig;

@SuppressWarnings({"rawtypes","unchecked"})
public class OnsConsumer extends AbstractMQConsumer implements MessageListener{
	private static final Log logger = LogFactory.getLog(OnsConsumer.class);
	private Consumer consumer;
	private GlobalEventHandlerFactory globalEventHandlerFactory;
	
	private String[] topics = null;
	
	@Override
	public void init() {
		logger.debug("try to build ons consumer");
		try{
			this.topics = getTopics();
			if(this.topics == null || this.topics.length <= 0){
				logger.debug("未配置mq.subscribe.topics项，不启动订阅服务");
				return;
			}
			
			Properties properties = new Properties();
			properties.put(PropertyKeyConst.ConsumerId, SystemConfig.getProperty("mq.ons.cid"));
			properties.put(PropertyKeyConst.AccessKey, SystemConfig.getProperty("mq.ons.ak","3qPjLmZrmSgXHQKn"));
			properties.put(PropertyKeyConst.SecretKey, SystemConfig.getProperty("mq.ons.sk","CUB2Fl0NXtOnB5qfpNFOGf4VhVAte1"));
			consumer = ONSFactory.createConsumer(properties);
			logger.debug("ons consumer build success,ready to start");
			this.start();
			logger.debug("ons consumer start successfully ");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	@Override
	public void start() {
		for (String topic : topics) {
			logger.debug("ons subscribe topc:"+topic);
			consumer.subscribe(topic, "*",this);	
		}
		consumer.start();
	}

	@Override
	public void destroy() {
		consumer.shutdown();
	}

	@Override
	public void await() {
		while(consumer.isStarted()){
			try {
				TimeUnit.SECONDS.sleep(10);
				logger.debug("-------------------------");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
	public Action consume(Message message, ConsumeContext context) {
		String msg = null;
		try {
			logger.debug("recivied a message with id:["+message.getMsgID()+"] and message key is :["+message.getKey()+"]");
			logger.debug("msg user properties:"+message.getUserProperties());
			String isBase64Encode = message.getUserProperties("base64");
			if(isBase64Encode != null && isBase64Encode.equals("true")){
				msg = new String(Base64.decodeBase64(message.getBody()),"UTF-8");
			}else{
				msg = new String(message.getBody(),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(msg == null)
			return null;
		logger.debug("recivied msg :" + msg);
		try {
			MQEvent event = parseMessage(msg);
			if (event != null) {
				logger.debug("parse msg to MQEvent object and id is :"	+ event.getId());
				GlobalEventHandler handler = globalEventHandlerFactory.getHandler(event.getEventName());
				if (handler != null) {
					logger.debug("found a handler for the event:" + event.getId() + "--" + handler.getClass().getName());
					handler.handle(event);
				} else {
					logger.debug("have not defined a handler for the event:" + event.getEventName());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return Action.CommitMessage;
	}


	public GlobalEventHandlerFactory getGlobalEventHandlerFactory() {
		return globalEventHandlerFactory;
	}


	public void setGlobalEventHandlerFactory(
			GlobalEventHandlerFactory globalEventHandlerFactory) {
		this.globalEventHandlerFactory = globalEventHandlerFactory;
	}
	
	
}
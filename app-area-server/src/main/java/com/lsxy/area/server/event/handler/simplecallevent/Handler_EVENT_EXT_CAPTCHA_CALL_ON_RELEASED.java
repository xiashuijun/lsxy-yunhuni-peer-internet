package com.lsxy.area.server.event.handler.simplecallevent;

import com.lsxy.area.api.BusinessState;
import com.lsxy.area.api.BusinessStateService;
import com.lsxy.area.server.event.EventHandler;
import com.lsxy.area.server.util.NotifyCallbackUtil;
import com.lsxy.framework.core.utils.MapBuilder;
import com.lsxy.framework.rpc.api.RPCRequest;
import com.lsxy.framework.rpc.api.RPCResponse;
import com.lsxy.framework.rpc.api.event.Constants;
import com.lsxy.framework.rpc.api.session.Session;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by liups on 2016/8/31.
 */
@Component
public class Handler_EVENT_EXT_CAPTCHA_CALL_ON_RELEASED extends EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(Handler_EVENT_EXT_CAPTCHA_CALL_ON_RELEASED.class);

    @Autowired
    private BusinessStateService businessStateService;

    @Autowired
    private NotifyCallbackUtil notifyCallbackUtil;

    @Override
    public String getEventName() {
        return Constants.EVENT_EXT_CAPTCHA_CALL_ON_RELEASED;
    }

    @Override
    public RPCResponse handle(RPCRequest request, Session session) {
        if(logger.isDebugEnabled()){
            logger.debug("开始处理{}事件,{}",getEventName(),request);
        }
        RPCResponse res = null;
        Map<String, Object> paramMap = request.getParamMap();
        String call_id = (String)paramMap.get("user_data");
        if(StringUtils.isBlank(call_id)){
            logger.info("call_id is null");
            return res;
        }
        BusinessState state = businessStateService.get(call_id);
        if(state == null){
            logger.info("businessstate is null");
            return res;
        }
        String appId = state.getAppId();
        String user_data = state.getUserdata();
        if(StringUtils.isBlank(appId)){
            logger.info("appId为空");
            return res;
        }
        String callBackUrl = state.getCallBackUrl();
        if(StringUtils.isBlank(callBackUrl)){
            logger.info("回调地址callBackUrl为空");
            return res;
        }
        //开始通知开发者
        if(logger.isDebugEnabled()){
            logger.debug("用户回调结束事件");
        }
        Map<String,Object> notify_data = new MapBuilder<String,Object>()
                .put("event","captcha_call.end")
                .put("id",call_id)
                .put("begin_time",paramMap.get("begin_time"))
                .put("answer",paramMap.get("answer_time"))
                .put("end_time",paramMap.get("end_time"))
                .put("keys",paramMap.get("keys"))
                .put("duration",paramMap.get("keys"))
                .put("hangup_by",paramMap.get("dropped_by"))
                .put("reason",paramMap.get("dropped_by"))
                .put("error",paramMap.get("error"))
                .put("user_data",user_data)
                .build();
        notifyCallbackUtil.postNotify(callBackUrl,notify_data,3);
        if(logger.isDebugEnabled()){
            logger.debug("语音验证码结束事件");
        }
        if(logger.isDebugEnabled()){
            logger.debug("处理{}事件完成",getEventName());
        }
        return res;
    }
}

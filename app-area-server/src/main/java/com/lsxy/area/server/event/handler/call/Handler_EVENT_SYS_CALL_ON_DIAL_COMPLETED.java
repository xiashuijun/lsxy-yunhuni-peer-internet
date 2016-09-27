package com.lsxy.area.server.event.handler.call;

import com.lsxy.area.api.BusinessState;
import com.lsxy.area.api.BusinessStateService;
import com.lsxy.area.api.ConfService;
import com.lsxy.area.server.event.EventHandler;
import com.lsxy.area.server.service.ivr.IVRActionService;
import com.lsxy.area.server.util.NotifyCallbackUtil;
import com.lsxy.area.server.util.PlayFileUtil;
import com.lsxy.framework.api.tenant.service.TenantService;
import com.lsxy.framework.core.utils.MapBuilder;
import com.lsxy.framework.rpc.api.RPCCaller;
import com.lsxy.framework.rpc.api.RPCRequest;
import com.lsxy.framework.rpc.api.RPCResponse;
import com.lsxy.framework.rpc.api.ServiceConstants;
import com.lsxy.framework.rpc.api.event.Constants;
import com.lsxy.framework.rpc.api.session.Session;
import com.lsxy.framework.rpc.api.session.SessionContext;
import com.lsxy.yunhuni.api.app.model.App;
import com.lsxy.yunhuni.api.app.service.AppService;
import com.lsxy.yunhuni.api.session.service.CallSessionService;
import com.lsxy.yunhuni.api.session.service.VoiceIvrService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuws on 2016/8/29.
 */
@Component
public class Handler_EVENT_SYS_CALL_ON_DIAL_COMPLETED extends EventHandler{

    private static final Logger logger = LoggerFactory.getLogger(Handler_EVENT_SYS_CALL_ON_DIAL_COMPLETED.class);

    @Autowired
    private BusinessStateService businessStateService;

    @Autowired
    private RPCCaller rpcCaller;

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private AppService appService;

    @Autowired
    private ConfService confService;

    @Autowired
    private NotifyCallbackUtil notifyCallbackUtil;

    @Autowired
    private IVRActionService ivrActionService;

    @Autowired
    private CallSessionService callSessionService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private VoiceIvrService voiceIvrService;

    @Autowired
    private PlayFileUtil playFileUtil;

    @Override
    public String getEventName() {
        return Constants.EVENT_SYS_CALL_ON_DIAL_COMPLETED;
    }

    /**
     * 拨号结束事件处理
     * @param request
     * @param session
     * @return
     */
    @Override
    public RPCResponse handle(RPCRequest request, Session session) {
        if(logger.isDebugEnabled()){
            logger.debug("开始处理{}事件,{}",getEventName(),request);
        }
        RPCResponse res = null;
        Map<String,Object> params = request.getParamMap();
        if(MapUtils.isEmpty(params)){
            logger.error("request params is null");
            return res;
        }
        String call_id = (String)params.get("user_data");
        //错误信息。如果拨号失败，该参数记录错误信息。如果拨号成功的被接听，该参数的值是 null。
        String error = (String)params.get("error");

        if(StringUtils.isBlank(call_id)){
            logger.info("call_id is null");
            return res;
        }
        BusinessState state = businessStateService.get(call_id);
        if(state == null){
            logger.info("businessstate is null");
            return res;
        }
        if(logger.isDebugEnabled()){
            logger.info("call_id={},state={}",call_id,state);
        }
        Map<String,Object> businessData = state.getBusinessData();

        if(businessData == null){
            businessData = new HashMap<>();
        }

        if("sys_conf".equals(state.getType())){//该呼叫是通过(会议邀请呼叫)发起需要将呼叫加入会议
            if(StringUtils.isNotBlank(error)){
                logger.error("将呼叫加入到会议失败{}",error);
            }else{
                String conf_id = (String)businessData.get("conf_id");
                if(conf_id == null){
                    logger.info("将呼叫加入到会议失败conf_id为null");
                    return res;
                }
                try {
                    confService.confEnter(call_id,conf_id,null,null,null);
                } catch (Throwable e) {
                    logger.error("将呼叫加入到会议失败",e);
                }
            }
        }else if("ivr_call".equals(state.getType())){//通过ivr呼出api 发起的呼叫
            if(StringUtils.isNotBlank(error)){
                logger.error("IVR呼出失败",error);
            }else{
                App app = appService.findById(state.getAppId());
                //发送拨号结束通知
                Long begin_time = null;
                Long end_time = null;
                if(params.get("begin_time") != null){
                    begin_time = ((long)params.get("begin_time")) * 1000;
                }
                if(params.get("end_time") != null){
                    end_time = ((long)params.get("end_time")) * 1000;
                }
                Map<String,Object> notify_data = new MapBuilder<String,Object>()
                        .putIfNotEmpty("event","ivr.dial_end")
                        .putIfNotEmpty("id",call_id)
                        .putIfNotEmpty("begin_time",begin_time)
                        .putIfNotEmpty("end_time",end_time)
                        .putIfNotEmpty("user_data",state.getUserdata())
                        .build();

                notifyCallbackUtil.postNotify(app.getUrl(),notify_data,3);
                ivrActionService.doAction(call_id);
            }
        }else if("ivr_dial".equals(state.getType())){//通过ivr拨号动作发起的呼叫
            String ivr_call_id = (String)businessData.get("ivr_call_id");
            if(StringUtils.isNotBlank(error)){
                //拨号失败直接继续ivr
                ivrActionService.doAction(ivr_call_id);
            }else{
                BusinessState ivrState = businessStateService.get(ivr_call_id);
                String res_id_one = ivrState.getResId();
                Integer max_seconds = (Integer) businessData.get("max_seconds");
                String res_id_two = state.getResId();
                Integer connect_mode = (Integer) businessData.get("connect_mode");
                Boolean recording = (Boolean)businessData.get("recording");
                String record_file = null;
                Integer local_volume = (Integer) businessData.get("volume1");
                Integer remote_volume = (Integer) businessData.get("volume2");
                Long schedule_play_time=(Long) businessData.get("play_time");
                String schedule_play_file = (String) businessData.get("play_file");
                Integer schedule_play_loop = (Integer) businessData.get("play_repeat");

                if(recording!=null && recording){
                    //TODO 录音文件名如何定
                    record_file = "";
                }

                try {
                    schedule_play_file = playFileUtil.convert(state.getTenantId(),state.getAppId(),schedule_play_file);
                    Map<String,Object> map = new MapBuilder<String,Object>()
                            .putIfNotEmpty("res_id",res_id_one)
                            .putIfNotEmpty("max_seconds",max_seconds)
                            .putIfNotEmpty("call_res_id",res_id_two)
                            .putIfNotEmpty("connect_mode",connect_mode)
                            .putIfNotEmpty("record_file",record_file)
                            .putIfNotEmpty("local_volume",local_volume)
                            .putIfNotEmpty("remote_volume",remote_volume)
                            .putIfNotEmpty("schedule_play_time",schedule_play_time)
                            .putIfNotEmpty("schedule_play_file",schedule_play_file)
                            .putIfNotEmpty("schedule_play_loop",schedule_play_loop)
                            .build();
                    RPCRequest rpcrequest = RPCRequest.newRequest(ServiceConstants.MN_CH_SYS_CALL_CONNECT_START, map);
                    rpcCaller.invoke(sessionContext, rpcrequest);
                } catch (Throwable e) {
                    logger.error("调用失败",e);
                }
            }
        }
        return res;
    }
}

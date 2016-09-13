package com.lsxy.area.server.util.ivr.act.handler;

import com.lsxy.area.api.BusinessState;
import com.lsxy.area.api.BusinessStateService;
import com.lsxy.area.server.util.PlayFileUtil;
import com.lsxy.framework.api.tenant.service.TenantService;
import com.lsxy.framework.core.utils.MapBuilder;
import com.lsxy.framework.core.utils.UUIDGenerator;
import com.lsxy.framework.rpc.api.RPCCaller;
import com.lsxy.framework.rpc.api.RPCRequest;
import com.lsxy.framework.rpc.api.ServiceConstants;
import com.lsxy.framework.rpc.api.session.SessionContext;
import com.lsxy.yunhuni.api.app.model.App;
import com.lsxy.yunhuni.api.app.service.AppService;
import com.lsxy.yunhuni.api.config.model.LineGateway;
import com.lsxy.yunhuni.api.config.service.LineGatewayService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuws on 2016/9/2.
 */
@Component
public class DialActionHandler extends ActionHandler{

    @Autowired
    private BusinessStateService businessStateService;

    @Autowired
    private RPCCaller rpcCaller;

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private AppService appService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private LineGatewayService lineGatewayService;

    @Autowired
    private PlayFileUtil playFileUtil;


    @Override
    public String getAction() {
        return "hangup";
    }

    /**
     * 1向区域代理发起呼叫
     * 2在拨号结束事件中向区域代理发送双通道连接命令
     * 3监听双通道调用成功事件，向开发者发送通知
     * 4监听双通道结束事件 向开发者发送通知
     * 5监听被叫方呼叫结束事件（此处要继续调用doAction）
     * @param callId
     * @param root
     * @return
     */
    @Override
    public boolean handle(String callId, Element root) {
        if(logger.isDebugEnabled()){
            logger.debug("开始处理ivr动作，callId={},act={}",callId,getAction());
        }
        if(logger.isDebugEnabled()){
            logger.debug("开始处理ivr[{}]动作",getAction());
        }
        BusinessState state = businessStateService.get(callId);
        if(state == null){
            logger.info("没有找到call_id={}的state",callId);
            return false;
        }
        String nextUrl = "";
        Element next = root.element("next");
        if(next!=null){
            if(StringUtils.isNotBlank(next.getTextTrim())){
                nextUrl = next.getTextTrim();
            }
        }
        dial(callId,state.getResId(),state.getAppId(),state.getTenantId(),root);

        //更新下一步
        Map<String,Object> businessData = state.getBusinessData();
        if(businessData == null){
            businessData = new HashMap<>();
        }
        businessData.put("next",nextUrl);
        state.setBusinessData(businessData);
        businessStateService.save(state);
        return true;
    }

    public void dial(String ivr_call_id,String parent_call_res_id,String appId,String tenantId, Element root){
        //拨号
        String callId = UUIDGenerator.uuid();
        App app = appService.findById(appId);
        //TODO
        String oneTelnumber = appService.findOneAvailableTelnumber(app);
        LineGateway lineGateway = lineGatewayService.getBestLineGatewayByNumber(oneTelnumber);

        String ring_play_file = null;String to = null;
        Integer maxCallDuration=null;Integer maxDialDuration=null;
        String from=null;Integer max_seconds=null;Integer connect_mode = 1;
        Integer volume1 = null;Integer volume2 = null;Long play_time = null;
        String play_file=null;Integer play_repeat= null;
        //解析xml

        try{
            ring_play_file = playFileUtil.convert(tenantId,appId,ring_play_file);
        }catch (Throwable t){
            logger.error("",t);
        }
        Map<String, Object> params = new MapBuilder<String,Object>()
                .putIfNotEmpty("to_uri",to+"@"+lineGateway.getIp()+":"+lineGateway.getPort())
                .putIfNotEmpty("from_uri",oneTelnumber)
                .putIfNotEmpty("parent_call_res_id",parent_call_res_id)
                .putIfNotEmpty("ring_play_file",ring_play_file)
                .putIfNotEmpty("max_answer_seconds",maxCallDuration)
                .putIfNotEmpty("max_ring_seconds",maxDialDuration)
                .putIfNotEmpty("user_data",callId)
                .put("appid ",app.getId())
                .build();

        RPCRequest rpcrequest = RPCRequest.newRequest(ServiceConstants.MN_CH_SYS_CALL, params);
        try {
            rpcCaller.invoke(sessionContext, rpcrequest);
        } catch (Exception e) {
            logger.error("ivr 拨号出错:",e);
        }

        //保存业务数据，后续事件要用到
        BusinessState callstate = new BusinessState.Builder()
                .setTenantId(tenantId)
                .setAppId(appId)
                .setId(callId)
                .setType("ivr_dial")
                .setAreaId(app.getArea().getId())
                .setLineGatewayId(lineGateway.getId())
                .setBusinessData(new MapBuilder<String,Object>()
                        .putIfNotEmpty("ivr_call_id",ivr_call_id)
                        .putIfNotEmpty("from",from)
                        .putIfNotEmpty("to",to)
                        .putIfNotEmpty("max_seconds",max_seconds)
                        .putIfNotEmpty("connect_mode",connect_mode)
                        .putIfNotEmpty("volume1",volume1)
                        .putIfNotEmpty("volume2",volume2)
                        .putIfNotEmpty("play_time",play_time)
                        .putIfNotEmpty("play_file",play_file)
                        .putIfNotEmpty("play_repeat",play_repeat)
                        .build())
                .build();
        businessStateService.save(callstate);
    }
}

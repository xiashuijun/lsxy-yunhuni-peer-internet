package com.lsxy.app.api.gateway.rest.msg;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsxy.app.api.gateway.response.ApiGatewayResponse;
import com.lsxy.app.api.gateway.rest.AbstractAPIController;
import com.lsxy.app.api.gateway.rest.msg.dto.SmsSendDTO;
import com.lsxy.app.api.gateway.rest.msg.dto.SmsSendMassDTO;
import com.lsxy.app.api.gateway.rest.msg.dto.UssdSendDTO;
import com.lsxy.app.api.gateway.rest.msg.dto.UssdSendMassDTO;
import com.lsxy.app.api.gateway.rest.msg.vo.MsgRequestVO;
import com.lsxy.framework.core.exceptions.api.AppServiceInvalidException;
import com.lsxy.framework.core.exceptions.api.IPNotInWhiteListException;
import com.lsxy.framework.core.exceptions.api.YunhuniApiException;
import com.lsxy.framework.core.utils.Page;
import com.lsxy.framework.web.utils.WebUtils;
import com.lsxy.msg.api.model.MsgUserRequest;
import com.lsxy.msg.api.result.MsgSendMassResult;
import com.lsxy.msg.api.result.MsgSendOneResult;
import com.lsxy.msg.api.service.MsgSendService;
import com.lsxy.msg.api.service.MsgUserRequestService;
import com.lsxy.yunhuni.api.app.model.App;
import com.lsxy.yunhuni.api.app.service.AppService;
import com.lsxy.yunhuni.api.app.service.ServiceType;
import com.lsxy.yunhuni.api.product.enums.ProductCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liups on 2017/3/7.
 */
@RestController
public class UssdController extends AbstractAPIController {
    private static final Logger logger = LoggerFactory.getLogger(UssdController.class);

    @Reference(timeout=3000,check = false,lazy = true)
    MsgSendService msgSendService;
    @Reference(timeout=3000,check = false,lazy = true)
    MsgUserRequestService msgUserRequestService;
    @Autowired
    AppService appService;

    @RequestMapping(value = "/{account_id}/msg/ussd/send",method = RequestMethod.POST)
    public ApiGatewayResponse ussdSend(HttpServletRequest request, @Valid @RequestBody UssdSendDTO dto, @PathVariable String account_id) throws YunhuniApiException {
        String appId = request.getHeader("AppID");
        App app = appService.findById(appId);
        if(!appService.enabledService(app.getTenant().getId(),app.getId(), ServiceType.USSD)){
            throw new AppServiceInvalidException();
        }

        String ip = WebUtils.getRemoteAddress(request);
        String whiteList = app.getWhiteList();
        if(StringUtils.isNotBlank(whiteList)){
            if(!whiteList.contains(ip)){
                throw new IPNotInWhiteListException();
            }
        }
        String subaccountId = getSubaccountId(request);
        MsgSendOneResult msgSendOneResult = msgSendService.sendUssd(appId, subaccountId, dto.getMobile(), dto.getTempId(), dto.getTempArgs());
        return ApiGatewayResponse.success(msgSendOneResult);
    }

    @RequestMapping(value = "/{account_id}/msg/ussd/mass/task",method = RequestMethod.POST)
    public ApiGatewayResponse ussdSendMass(HttpServletRequest request, @Valid @RequestBody UssdSendMassDTO dto, @PathVariable String account_id) throws YunhuniApiException {
        String appId = request.getHeader("AppID");
        App app = appService.findById(appId);
        if(!appService.enabledService(app.getTenant().getId(),app.getId(), ServiceType.USSD)){
            throw new AppServiceInvalidException();
        }
        String ip = WebUtils.getRemoteAddress(request);
        String whiteList = app.getWhiteList();
        if(StringUtils.isNotBlank(whiteList)){
            if(!whiteList.contains(ip)){
                throw new IPNotInWhiteListException();
            }
        }
        String subaccountId = getSubaccountId(request);
        MsgSendMassResult msgSendMassResult = msgSendService.sendUssdMass(appId, subaccountId, dto.getTaskName(), dto.getTempId(), dto.getTempArgs(), dto.getMobiles(), dto.getSendTime());
        return ApiGatewayResponse.success(msgSendMassResult);
    }

    @RequestMapping(value = "/{account_id}/msg/ussd/{msgKey}",method = RequestMethod.GET)
    public ApiGatewayResponse ussdSendMass(HttpServletRequest request, @PathVariable String msgKey, @PathVariable String account_id) throws YunhuniApiException {
        String appId = request.getHeader("AppID");
        App app = appService.findById(appId);
        if(!appService.enabledService(app.getTenant().getId(),app.getId(), ServiceType.USSD)){
            throw new AppServiceInvalidException();
        }
        String ip = WebUtils.getRemoteAddress(request);
        String whiteList = app.getWhiteList();
        if(StringUtils.isNotBlank(whiteList)){
            if(!whiteList.contains(ip)){
                throw new IPNotInWhiteListException();
            }
        }
        String subaccountId = getSubaccountId(request);
        MsgUserRequest msgUserRequest = msgUserRequestService.findByMsgKeyAndSendType(appId,subaccountId,msgKey, ProductCode.msg_ussd.name());
        return ApiGatewayResponse.success(new MsgRequestVO(msgUserRequest));
    }

    @RequestMapping(value = "/{account_id}/msg/ussd",method = RequestMethod.GET)
    public ApiGatewayResponse ussdSendMass(HttpServletRequest request, @PathVariable String account_id,
                                           @RequestParam(defaultValue = "1") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize) throws YunhuniApiException {
        String appId = request.getHeader("AppID");
        App app = appService.findById(appId);
        if(!appService.enabledService(app.getTenant().getId(),app.getId(), ServiceType.USSD)){
            throw new AppServiceInvalidException();
        }
        String ip = WebUtils.getRemoteAddress(request);
        String whiteList = app.getWhiteList();
        if(StringUtils.isNotBlank(whiteList)){
            if(!whiteList.contains(ip)){
                throw new IPNotInWhiteListException();
            }
        }
        String subaccountId = getSubaccountId(request);
        Page<MsgUserRequest> page = msgUserRequestService.findPageBySendTypeForGW(appId, subaccountId, ProductCode.msg_ussd.name(), pageNo, pageSize);
        List<MsgUserRequest> result = page.getResult();
        if(result != null && result.size() > 0){
            List<MsgRequestVO> vos = new ArrayList<>();
            for(MsgUserRequest re:result){
                vos.add(new MsgRequestVO(re));
            }
            page.setResult(vos);
        }
        return ApiGatewayResponse.success(page);
    }


}

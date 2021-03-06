package com.lsxy.app.portal.rest.cost;

import com.lsxy.app.portal.base.AbstractRestController;
import com.lsxy.framework.core.utils.BeanUtils;
import com.lsxy.framework.core.utils.Page;
import com.lsxy.framework.web.rest.RestResponse;
import com.lsxy.yunhuni.api.consume.enums.ConsumeCode;
import com.lsxy.yunhuni.api.consume.model.Consume;
import com.lsxy.yunhuni.api.consume.service.ConsumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消费记录
 * Created by zhangxb on 2016/7/8.
 */
@RequestMapping("/rest/consume")
@RestController
public class ConsumeController extends AbstractRestController {
    private static final Logger logger = LoggerFactory.getLogger(ConsumeController.class);
    @Autowired
    ConsumeService consumeService;
    /**
     * 获取数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @RequestMapping("/list")
    public RestResponse pageList(String startTime,String endTime,String appId){
        String userName = getCurrentAccountUserName();
        List<Consume> list =  consumeService.listConsume(userName,startTime,endTime,appId);
        changeTypeToChinese(list);
        return RestResponse.success(list);
    }
    /**
     * 获取分页数据
     * @param pageNo 第几页
     * @param pageSize 每页记录数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @RequestMapping("/page")
    public RestResponse pageList(Integer pageNo , Integer pageSize,String startTime,String endTime,String appId){
        String userName = getCurrentAccountUserName();
        Page<Consume> page =  consumeService.pageList(userName,pageNo,pageSize,startTime,endTime,appId);
        changeTypeToChinese(page.getResult());
        return RestResponse.success(page);
    }

    /**
     * 将消费类型转换为中文，运用枚举
     * @param consumes
     */
    private void changeTypeToChinese(List<Consume> consumes){
        for(int i = 0;i < consumes.size();i++){
            Consume consume = new Consume();
            try {
                BeanUtils.copyProperties(consume,consumes.get(i));
            } catch (Exception e) {
                logger.error("转换类属性出错",e);
            }
            String type = consume.getType();
            try{
                ConsumeCode consumeCode = ConsumeCode.valueOf(type);
                consume.setType(consumeCode.getName());
            }catch(Exception e){
//                e.printStackTrace();
                consume.setType("未知项目");
            }
            consumes.set(i,consume);
        }
    }

}

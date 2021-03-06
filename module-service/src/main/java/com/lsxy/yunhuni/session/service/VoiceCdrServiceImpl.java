package com.lsxy.yunhuni.session.service;

import com.lsxy.framework.api.base.BaseDaoInterface;
import com.lsxy.framework.api.billing.service.CalBillingService;
import com.lsxy.framework.base.AbstractService;
import com.lsxy.framework.core.utils.*;
import com.lsxy.utils.StatisticsUtils;
import com.lsxy.yunhuni.api.apicertificate.model.CertAccountQuota;
import com.lsxy.yunhuni.api.apicertificate.model.CertAccountQuotaType;
import com.lsxy.yunhuni.api.apicertificate.service.CertAccountQuotaService;
import com.lsxy.yunhuni.api.app.model.App;
import com.lsxy.yunhuni.api.consume.model.Consume;
import com.lsxy.yunhuni.api.consume.service.ConsumeService;
import com.lsxy.yunhuni.api.product.enums.ProductCode;
import com.lsxy.yunhuni.api.product.service.CalCostService;
import com.lsxy.yunhuni.api.session.model.CallSession;
import com.lsxy.yunhuni.api.session.model.VoiceCdr;
import com.lsxy.yunhuni.api.session.service.CallSessionService;
import com.lsxy.yunhuni.api.session.service.VoiceCdrService;
import com.lsxy.yunhuni.api.statistics.model.VoiceCdrDay;
import com.lsxy.yunhuni.api.statistics.model.VoiceCdrHour;
import com.lsxy.yunhuni.api.statistics.service.VoiceCdrDayService;
import com.lsxy.yunhuni.api.statistics.service.VoiceCdrHourService;
import com.lsxy.yunhuni.session.dao.VoiceCdrDao;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangxb on 2016/7/19.
 */
@Service
public class VoiceCdrServiceImpl extends AbstractService<VoiceCdr> implements  VoiceCdrService{
    private static final Logger logger = LoggerFactory.getLogger(VoiceCdrServiceImpl.class);
    @Autowired
    private VoiceCdrDao voiceCdrDao;
    @Override
    public BaseDaoInterface<VoiceCdr, Serializable> getDao() {
        return voiceCdrDao;
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CallSessionService callSessionService;
    @Autowired
    private VoiceCdrHourService voiceCdrHourService;
    @Autowired
    private VoiceCdrDayService voiceCdrDayService;

    @Autowired
    private CalCostService calCostService;

    @Autowired
    private CalBillingService calBillingService;

    @Autowired
    ConsumeService consumeService;
    @Autowired
    CertAccountQuotaService certAccountQuotaService;

    @Override
    public List<VoiceCdr> listCdr(String type, String tenantId, Date startTime,Date endTime, String appId) {
        String[] types = null;
        if(type!=null){
            types = new String[]{type};
        }
        if(App.PRODUCT_CALL_CENTER.equals(type)){
            types = CallSession.CALL_CENTER_PRODUCT_CODE;
        }
        String sql = "from db_lsxy_bi_yunhuni.tb_bi_voice_cdr where "+ StatisticsUtils.getSqlIsNull2(tenantId,appId,types)+ " deleted=0 and   call_start_dt BETWEEN ? and ?";
        sql = "select "+StringUtil.sqlName(VoiceCdr.class)+sql+" order by call_start_dt desc ";
        List rows = jdbcTemplate.queryForList(sql,new Object[]{startTime,endTime});
        List<VoiceCdr> list = new ArrayList();
        for(int i=0;i<rows.size();i++){
            VoiceCdr voiceCdr = new VoiceCdr();
            try {
                BeanUtils.copyProperties(voiceCdr,rows.get(i));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            list.add(voiceCdr);
        }
        return list;
    }

    @Override
    public Page<VoiceCdr> pageList(Integer pageNo,Integer pageSize, String type,String tenantId, Date startTime,Date endTime,String appId) {
        String[] types = null;
        if(type!=null){
            types = new String[]{type};
        }
        if(App.PRODUCT_CALL_CENTER.equals(type)){
            types = CallSession.CALL_CENTER_PRODUCT_CODE;
        }
        String sql = "from db_lsxy_bi_yunhuni.tb_bi_voice_cdr where "+ StatisticsUtils.getSqlIsNull2(tenantId,appId,types)+ " deleted=0 and   call_start_dt BETWEEN ? and ?";
        String sqlCount = "select count(1) "+sql;
        Integer totalCount = jdbcTemplate.queryForObject(sqlCount,Integer.class,new Object[]{startTime,endTime});
        sql = "select "+StringUtil.sqlName(VoiceCdr.class)+sql+" order by call_start_dt desc limit ?,?";
        pageNo--;
        List rows = jdbcTemplate.queryForList(sql,new Object[]{startTime,endTime,pageNo*pageSize,pageSize});
        List list = new ArrayList();
        for(int i=0;i<rows.size();i++){
            VoiceCdr voiceCdr = new VoiceCdr();
            try {
                BeanUtils.copyProperties(voiceCdr,rows.get(i));
            } catch (IllegalAccessException e) {
                logger.error("异常",e);
            } catch (InvocationTargetException e) {
                logger.error("异常",e);
            }
            list.add(voiceCdr);
        }
        Page<VoiceCdr> page = new Page((pageNo)*pageSize+1,totalCount,pageSize,list);
        return page;
    }

    @Override
    public Map sumCost( String type ,String tenantId, Date startTime,Date endTime, String appId) {
        String[] types = null;
        if(type!=null){
            types = new String[]{type};
        }
        if(App.PRODUCT_CALL_CENTER.equals(type)){
            types = CallSession.CALL_CENTER_PRODUCT_CODE;
        }
        String costType = " SUM(cost) as cost";
        String sql = "select "+costType+" from db_lsxy_bi_yunhuni.tb_bi_voice_cdr  where "+ StatisticsUtils.getSqlIsNull2(tenantId,appId,types)+ " deleted=0  and call_start_dt BETWEEN ? and ? ";
        Map result = this.jdbcTemplate.queryForMap(sql,new Object[]{startTime,endTime});
        return result;
    }


    @Override
    public Map currentRecordStatistics(String appId){
        VoiceCdrHour voiceCdrHour = null;
        VoiceCdrDay voiceCdrDay = null;
        Date date = new Date();
        Long currentSession = callSessionService.currentCallSessionCount(appId);
        String currentHourStr = DateUtils.formatDate(date, "yyyy-MM-dd HH");
        Date currentHour = DateUtils.parseDate(currentHourStr, "yyyy-MM-dd HH");
        currentHour = DateUtils.getPrevHour(currentHour);
        voiceCdrHour = voiceCdrHourService.findByAppIdAndTime(appId,currentHour);
        if(voiceCdrHour == null){
            Date lastHour = DateUtils.getPrevHour(currentHour);
            voiceCdrHour = voiceCdrHourService.findByAppIdAndTime(appId,lastHour);
        }

        String currentDayStr = DateUtils.formatDate(date, "yyyy-MM-dd");
        Date currentDay = DateUtils.parseDate(currentDayStr, "yyyy-MM-dd");
        currentDay = DateUtils.getPreDate(currentDay);
        voiceCdrDay = voiceCdrDayService.findByAppIdAndTime(appId,currentDay);
        if(voiceCdrDay == null){
            Date lastDay = DateUtils.getPreDate(currentDay);
            voiceCdrDay = voiceCdrDayService.findByAppIdAndTime(appId,lastDay);
        }

        Map result = new HashMap();
        result.put("hourCount",voiceCdrHour == null ? 0 : voiceCdrHour.getAmongCall());
        result.put("dayCount",voiceCdrDay == null ? 0 : voiceCdrDay.getAmongCall());
        result.put("currentSession",currentSession);
        return result;
    }

//    @Override
//    public Map getAvgCdr(String tenantId, String appId, String startTime, String endTime) {
//        String sql = " SELECT CONVERT(IFNULL((a.costTimeLong/a.`session`)/60,0),SIGNED) AS  avgCostTime,CONVERT(IFNULL(a.callAckDt/a.`session`,0)*100,SIGNED) AS avgCall, a.cost AS cost, a.`session` AS session,CONVERT(a.costTimeLong/60,SIGNED) AS costTime FROM(  " +
//                " SELECT COUNT(id) AS session,IFNULL(SUM(cost_time_long),0) AS costTimeLong,IFNULL(SUM(CASE  WHEN call_ack_dt IS NULL THEN 0 ELSE 1 END),0) AS callAckDt,IFNULL(SUM(cost),0) AS cost FROM db_lsxy_bi_yunhuni.tb_bi_voice_cdr WHERE 1=1 ";
//        String sql2 = "SELECT IFNULL(sum(amount),0) AS cost FROM db_lsxy_bi_yunhuni.tb_bi_consume WHERE 1=1 ";
//        if(StringUtils.isNotEmpty(tenantId)){
//            sql +=" AND tenant_id='"+tenantId+"' " ;
//            sql2 +=" AND tenant_id='"+tenantId+"' " ;
//        }
//        if(StringUtils.isNotEmpty(appId)){
//            sql += " AND app_id='"+appId+"' ";
//            sql2 += " AND app_id='"+appId+"' ";
//        }
//        if(StringUtils.isNotEmpty(startTime)){
//            sql += " AND call_end_dt>='"+startTime+"' " ;
//            sql2 += " AND call_end_dt>='"+startTime+"' " ;
//        }
//        if(StringUtils.isNotEmpty(endTime)){
//            sql += " AND call_end_dt<='"+endTime+"' " ;
//            sql2 += " AND call_end_dt<='"+endTime+"' " ;
//        }
//        sql +=" ) a ";
//        Map map = jdbcTemplate.queryForMap(sql);
//        Map map2 = jdbcTemplate.queryForMap(sql2);
//        map.putAll(map2);
//        return map;
//    }

    @Override
    public Map getStaticCdr(String tenantId, String appId,String subaccountId, Date startTime, Date endTime) {
        String sql = "SELECT COUNT(id) AS callSum,IFNULL(SUM(cost_time_long),0) AS costTimeLong," +
                "IFNULL(SUM(CASE  WHEN call_ack_dt IS NULL THEN 0 ELSE 1 END),0) AS askSum FROM db_lsxy_bi_yunhuni.tb_bi_voice_cdr WHERE 1=1 ";
        if(StringUtils.isNotEmpty(tenantId)){
            sql +=" AND tenant_id='"+tenantId+"' " ;
        }
        if(StringUtils.isNotEmpty(appId)){
            sql += " AND app_id='"+appId+"' ";
        }
        if(StringUtils.isNotEmpty(subaccountId)){
            sql += " AND subaccount_id='"+subaccountId+"' ";
        }
        if(startTime != null){
            String startTimeStr = DateUtils.getDate(startTime,"yyyy-MM-dd HH:mm:ss");
            sql += " AND call_end_dt>='"+startTimeStr+"' " ;
        }
        if(endTime != null){
            String endTimeStr = DateUtils.getDate(endTime,"yyyy-MM-dd HH:mm:ss");
            sql += " AND call_end_dt<'"+endTimeStr+"' " ;
        }
        Map map = jdbcTemplate.queryForMap(sql);
        return map;
    }


    @Override
    public VoiceCdr save(VoiceCdr voiceCdr){
        return this.getDao().save(voiceCdr);
    }

    @Override
    public void insertCdr(VoiceCdr voiceCdr){
        //扣费
        if(voiceCdr.getCallAckDt() != null){
            calCostService.callConsumeCal(voiceCdr);
        }else{
            voiceCdr.setCostTimeLong(0L);
            voiceCdr.setCost(BigDecimal.ZERO);
            voiceCdr.setDeduct(0L);
            voiceCdr.setCostType(VoiceCdr.COST_TYPE_COST);
        }
        calBillingService.incCallSum(voiceCdr.getTenantId(),voiceCdr.getCallEndDt());
        if(voiceCdr.getCallAckDt() != null){
            calBillingService.incCallConnect(voiceCdr.getTenantId(),voiceCdr.getCallEndDt());
        }
        calBillingService.incCallCostTime(voiceCdr.getTenantId(),voiceCdr.getCallEndDt(),voiceCdr.getCostTimeLong());
        //保存CDR
        if(logger.isDebugEnabled()){
            logger.debug("插入cdr数据：{}", JSONUtil.objectToJson(voiceCdr));
        }
        getDao().save(voiceCdr);
        if(voiceCdr.getCost().compareTo(BigDecimal.ZERO) == 1){
            //插入消费
            ProductCode productCode = ProductCode.valueOf(voiceCdr.getType());
            Consume consume = new Consume(voiceCdr.getCallEndDt(),productCode.name(),voiceCdr.getCost(),productCode.getRemark(),voiceCdr.getAppId(),voiceCdr.getTenantId(),voiceCdr.getId(),voiceCdr.getSubaccountId());
            consumeService.consume(consume);
        }
        if(StringUtils.isNotBlank(voiceCdr.getSubaccountId())){
            certAccountQuotaService.incQuotaUsed(voiceCdr.getSubaccountId(),new Date(),voiceCdr.getCostTimeLong(), CertAccountQuotaType.CallQuota.name());
        }
    }
}

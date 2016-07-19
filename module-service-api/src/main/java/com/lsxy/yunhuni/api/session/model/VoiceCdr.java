package com.lsxy.yunhuni.api.session.model;

import com.lsxy.framework.api.base.IdEntity;
import com.lsxy.framework.api.tenant.model.Tenant;
import com.lsxy.yunhuni.api.app.model.App;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 通话记录
 * Created by zhangxb on 2016/7/19.
 */
@Entity
@Table(schema="db_lsxy_bi_yunhuni",name = "tb_bi_voice_cdr")
public class VoiceCdr extends IdEntity {
    public static final Integer COST_TYPE_DEDUCT = 1;
    public static final Integer COST_TYPE_COST = 2;
    private String session;//会话ID
    private String areaId;//所属区域
    private String tenantId;//所属租户
    private String  appId;//所属应用
    private String lineId;//所属线路
    private Integer type;//'1.语音呼叫2.双向回拨3.会议4.IVR定制服务5.语音验证码
    private String relevanceId;//根据会话类型关联对应类型的表的记录
    private String recordUrl;//录音文件URL
    private Integer recordSize;//录音文件大小
    private String formNum;//主叫
    private String toNum;//被叫
    private Date callStartDt;//呼叫开始时间
    private Date callAckDt;//呼叫应答时间
    private Date callEndDt;//呼叫结束时间
    private long callTimeLong;//呼叫时长
    private BigDecimal cost;//消费金额
    private long deduct;//扣量
    private String unit;//计量单位
    private Integer costType;//1.扣量2.扣费
    @Column( name = "session_id")
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
    @Column( name = "area_id")
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    @Column( name = "tenant_id")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenant(String tenantId) {
        this.tenantId = tenantId;
    }
    @Column( name = "app_id")
    public String getApp() {
        return appId;
    }

    public void setApp(String appId) {
        this.appId = appId;
    }
    @Column( name = "line_id")
    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
    @Column( name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @Column( name = "relevance_id")
    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }
    @Column( name = "record_url")
    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }
    @Column( name = "record_size")
    public Integer getRecordSize() {
        return recordSize;
    }

    public void setRecordSize(Integer recordSize) {
        this.recordSize = recordSize;
    }
    @Column( name = "form_num")
    public String getFormNum() {
        return formNum;
    }

    public void setFormNum(String formNum) {
        this.formNum = formNum;
    }
    @Column( name = "to_num")
    public String getToNum() {
        return toNum;
    }

    public void setToNum(String toNum) {
        this.toNum = toNum;
    }
    @Column( name = "call_start_dt")
    public Date getCallStartDt() {
        return callStartDt;
    }

    public void setCallStartDt(Date callStartDt) {
        this.callStartDt = callStartDt;
    }
    @Column( name = "call_ack_dt")
    public Date getCallAckDt() {
        return callAckDt;
    }

    public void setCallAckDt(Date callAckDt) {
        this.callAckDt = callAckDt;
    }
    @Column( name = "call_end_dt")
    public Date getCallEndDt() {
        return callEndDt;
    }

    public void setCallEndDt(Date callEndDt) {
        this.callEndDt = callEndDt;
    }
    @Column( name = "call_time_long")
    public long getCallTimeLong() {
        return callTimeLong;
    }

    public void setCallTimeLong(long callTimeLong) {
        this.callTimeLong = callTimeLong;
    }
    @Column( name = "cost")
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    @Column( name = "deduct")
    public long getDeduct() {
        return deduct;
    }

    public void setDeduct(long deduct) {
        this.deduct = deduct;
    }
    @Column( name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    @Column( name = "cost_type")
    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }
}

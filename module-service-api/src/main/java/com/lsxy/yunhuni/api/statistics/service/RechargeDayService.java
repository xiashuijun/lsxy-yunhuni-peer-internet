package com.lsxy.yunhuni.api.statistics.service;

import com.lsxy.framework.api.base.BaseService;
import com.lsxy.yunhuni.api.statistics.model.RechargeDay;

import java.sql.SQLException;
import java.util.Date;

/**
 * 订单记录日统计service
 * Created by zhangxb on 2016/8/1.
 */
public interface RechargeDayService extends BaseService<RechargeDay> {
    /**
     * 根据当前时间，进行统计
     * @param date1 时间yyyy-MM-dd
     * @param day1 第几天 1-31
     * @param date2 前一天的时间 yyyy-MM-dd
     * @param day2 前一天是第几天 1-31
     * @param select 组合groupby条件
     */
    public void dayStatistics(Date date1, int day1,Date date2,int day2, String[] select,String[] all) throws SQLException;
}

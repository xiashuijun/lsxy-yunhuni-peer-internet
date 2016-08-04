package com.lsxy.framework.statistics.service;

import com.lsxy.framework.api.base.BaseDaoInterface;
import com.lsxy.framework.api.statistics.model.RechargeHour;
import com.lsxy.framework.api.statistics.service.RechargeHourService;
import com.lsxy.framework.base.AbstractService;
import com.lsxy.framework.core.utils.DateUtils;
import com.lsxy.framework.core.utils.StringUtil;
import com.lsxy.framework.statistics.dao.RechargeHourDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 订单统计serviceimpl
 * Created hangxb on 2016/7/6.
 */
@Service
public class RechargeHourServiceImpl extends AbstractService<RechargeHour> implements RechargeHourService {
    @Autowired
    RechargeHourDao rechargeHourDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public BaseDaoInterface<RechargeHour, Serializable> getDao() {
        return rechargeHourDao;
    }

    @Override
    public void hourStatistics(Date date1, int hour1,Date date2,int hour2,String[] select) throws  SQLException{
        String selects = "";
        String groupbys = "";
        String wheres = "";
        for(int i=0;i<select.length;i++){
            if(i==select.length-1){
                groupbys += select[i] ;
            }else {
                groupbys += select[i] + " , ";
            }
            selects += select[i] + " , ";
            wheres += select[i]+"=a."+select[i] +" and ";
        }
        String sql = " insert into db_lsxy_base.tb_base_recharge_hour("+selects+"dt,hour,among_amount,sum_amount,sum_num,create_time,last_time,deleted,sortno,version ) " +
                " select "+selects+" ? as dt,? as day, "+
                " IFNULL(sum(amount),0) as among_amount," +
                " IFNULL(sum(amount)+IFNULL((select sum_amount from db_lsxy_base.tb_base_recharge_hour h where "+wheres+" h.dt = ? and h.hour=? ),0),0) as  sum_amount," +
                " COUNT(1) as sum_num, " +
                " ? as create_time,? as last_time,? as deleted,? as sortno,? as version ";
        if(StringUtil.isNotEmpty(groupbys)){
            groupbys = " group by "+groupbys;
        }
        sql +=  " from db_lsxy_base.tb_base_recharge a where status='PAID' and last_time>=? and last_time<=? "+groupbys;
        update(date1, hour1,date2,hour2, sql);
    }
    
    private void update(final Date date1, final int day1,final Date date2, final int day2, String sql) throws  SQLException{
        jdbcTemplate.update(sql,new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                Timestamp sqlDate1 = new Timestamp(date1.getTime());//进行日期的转换
                ps.setObject(1,sqlDate1);
                ps.setInt(2,day1);
                Timestamp sqlDate2 = new Timestamp(date2.getTime());//进行日期的转换
                ps.setObject(3,sqlDate2);
                ps.setObject(4,day2);
                long times = new Date().getTime();
                Timestamp initDate = new Timestamp(times);
                ps.setObject(5,initDate);
                ps.setObject(6,initDate);
                ps.setObject(7,1);
                ps.setObject(8,times);
                ps.setObject(9,0);
                ps.setObject(10,sqlDate1);
                Date date3 = DateUtils.parseDate(DateUtils.formatDate(date1,"yyyy-MM-dd HH")+ ":59:59","yyyy-MM-dd HH:mm:ss");
                Timestamp sqlDate3 = new Timestamp(date3.getTime());
                ps.setObject(11,sqlDate3);
            }
        });
    }


}

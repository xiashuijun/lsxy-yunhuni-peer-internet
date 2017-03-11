package com.lsxy.msg.dao;

import com.lsxy.framework.api.base.BaseDaoInterface;
import com.lsxy.msg.api.model.MsgSendRecord;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

/**
 * Created by liups on 2017/3/1.
 */
public interface MsgSendRecordDao extends BaseDaoInterface<MsgSendRecord, Serializable> {
    MsgSendRecord findFirstByTaskId(String taskId);

    @Modifying
    @Query("update MsgSendRecord r set r.state = :state where r.msgKey = :msgKey")
    void updateStateByMsgKey(@Param("msgKey") String msgKey, @Param("state") int state);
}

package com.lsxy.framework.api.message.model;

import com.lsxy.framework.api.base.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 消息
 * Created by zhangxb on 2016/7/4.
 */
@Entity
@Table(schema="db_lsxy_base",name = "tb_base_message")
public class Message extends IdEntity {
    public static final Integer ONLINE = 1;//已上线
    public static final Integer OFFLINE = -1;//已下线
    public static final Integer NOT = 0;//未上线
    private Integer type;//消息类型
    private String content;//消息内容
    private String title;//标题
    private String name;//发布人
    private Date lineTime;//上线时间
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "line_time")
    public Date getLineTime() {
        return lineTime;
    }

    public void setLineTime(Date lineTime) {
        this.lineTime = lineTime;
    }
}

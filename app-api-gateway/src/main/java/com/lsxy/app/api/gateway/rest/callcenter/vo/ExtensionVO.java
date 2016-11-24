package com.lsxy.app.api.gateway.rest.callcenter.vo;

/**
 * Created by liups on 2016/11/18.
 */
public class ExtensionVO {
    private String id;
    private String type;//    type                 varchar(30),
    private String user;//    user                 varchar(50) comment 'SIP注册用户名，全局唯一。格式是6到12位数字',
    private String password;//    password             varchar(50) comment 'SIP注册密码',
    private String ipaddr;  //SIP 网关IP地址与端口，默认5060，仅用于 type==2的情况
    private String telenum;//    telenum              varchar(32) comment '如果是电话分机，该属性记录电话号码（保留，不用）',

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getTelenum() {
        return telenum;
    }

    public void setTelenum(String telenum) {
        this.telenum = telenum;
    }
}
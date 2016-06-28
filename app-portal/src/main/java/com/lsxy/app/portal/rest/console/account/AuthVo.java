package com.lsxy.app.portal.rest.console.account;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/25.
 */
public class AuthVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idType;//证件类型
    private String privateName;//用户名字
    private String  idNumber;//身份证号
    private String  idPhoto;//身份证照片
    private String  privateTenantId;//认证租户

    private String corpName;//企业名称
    private String addr;//企业地址
    private String fieldCode;//所属行业

    private String authType;//认证类型
    private String type01Prop01;//[一照一码]营业执照照片
    private String type01Prop02;//[一照一码]统一社会信用代码
    private String type02Prop01;//[三证合一]注册号
    private String type02Prop02;//[三证合一]税务登记证号

    private String type03Prop02;//[三证分离]税务登记证照片

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getPrivateName() {
        return privateName;
    }

    public void setPrivateName(String privateName) {
        this.privateName = privateName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getPrivateTenantId() {
        return privateTenantId;
    }

    public void setPrivateTenantId(String privateTenantId) {
        this.privateTenantId = privateTenantId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getType01Prop01() {
        return type01Prop01;
    }

    public void setType01Prop01(String type01Prop01) {
        this.type01Prop01 = type01Prop01;
    }

    public String getType01Prop02() {
        return type01Prop02;
    }

    public void setType01Prop02(String type01Prop02) {
        this.type01Prop02 = type01Prop02;
    }

    public String getType02Prop01() {
        return type02Prop01;
    }

    public void setType02Prop01(String type02Prop01) {
        this.type02Prop01 = type02Prop01;
    }

    public String getType02Prop02() {
        return type02Prop02;
    }

    public void setType02Prop02(String type02Prop02) {
        this.type02Prop02 = type02Prop02;
    }

    public String getType03Prop02() {
        return type03Prop02;
    }

    public void setType03Prop02(String type03Prop02) {
        this.type03Prop02 = type03Prop02;
    }

    @Override
    public String toString() {
        return "AuthVo{" +
                "idType='" + idType + '\'' +
                ", privateName='" + privateName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", idPhoto='" + idPhoto + '\'' +
                ", privateTenantId='" + privateTenantId + '\'' +
                ", corpName='" + corpName + '\'' +
                ", addr='" + addr + '\'' +
                ", fieldCode='" + fieldCode + '\'' +
                ", authType='" + authType + '\'' +
                ", type01Prop01='" + type01Prop01 + '\'' +
                ", type01Prop02='" + type01Prop02 + '\'' +
                ", type02Prop01='" + type02Prop01 + '\'' +
                ", type02Prop02='" + type02Prop02 + '\'' +
                ", type03Prop02='" + type03Prop02 + '\'' +
                '}';
    }
}

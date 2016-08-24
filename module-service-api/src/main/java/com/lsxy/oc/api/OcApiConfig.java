package com.lsxy.oc.api;

import org.springframework.boot.orm.jpa.EntityScan;

/**
 * Created by liups on 2016/8/9.
 * 用于外界导入实体配置
 * @Import(JpaEntityScanConfig.class)
 * 这样一来spring jpa实体就会被发现了
 * portal - api /MainClass.java有使用方式
 */
@EntityScan()
public class OcApiConfig {
}

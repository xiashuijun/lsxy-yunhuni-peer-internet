package com.lsxy.app.backend;

import com.lsxy.framework.FrameworkServiceConfig;
import com.lsxy.framework.api.FrameworkApiConfig;
import com.lsxy.framework.cache.FrameworkCacheConfig;
import com.lsxy.framework.mq.FrameworkMQConfig;
import com.lsxy.framework.web.web.AbstractSpringBootWebStarter;
import com.lsxy.yunhuni.YunhuniServiceConfig;
import com.lsxy.yunhuni.api.YunhuniApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Tandy on 2016/6/13.
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableScheduling
@Import({FrameworkApiConfig.class,FrameworkServiceConfig.class, FrameworkCacheConfig.class, YunhuniApiConfig.class, YunhuniServiceConfig.class, FrameworkMQConfig.class})
public class MainClass extends AbstractSpringBootWebStarter {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainClass.class, args);
    }

    @Override
    public String systemId() {
        return "app.backend";
    }
}

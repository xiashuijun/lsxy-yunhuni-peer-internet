package com.lsxy.app.opensips;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by tandy on 16/12/1.
 */
@Component
public class OpenSipEventListenerServer {
    private static final Logger logger = LoggerFactory.getLogger(OpenSipEventListenerServer.class);


    @PostConstruct
    public void startListener() throws InterruptedException {
        if(logger.isDebugEnabled()){
            logger.debug("启动事件监听...");
        }

        new Thread(() -> {
            Bootstrap b = new Bootstrap();
            EventLoopGroup group = new NioEventLoopGroup();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new OpenSipEventHandler());

            // 服务端监听在9999端口
            try {
                b.bind(9009).sync().channel().closeFuture().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }
}

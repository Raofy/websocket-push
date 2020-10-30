package com.ryan.websocketpush.netty.mainThread;

import com.ryan.websocketpush.netty.service.NioWebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Netty服务线程启动
 *
 */
@Component
@Slf4j
public class NettyServerThread implements CommandLineRunner {

    @Autowired
    private NioWebSocketServer nettyServer;

    @Value("${webSokcet.port}")
    private Integer webSocketPort;

    @Override
    public void run(String... args) throws Exception {
        log.warn("websocket服务器准备启动！ 端口为 :  " + webSocketPort);
        nettyServer.init(webSocketPort);

    }
}

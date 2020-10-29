package com.ryan.websocketpush.netty.service;

import com.ryan.websocketpush.netty.handler.NioWebSocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class NioWebSocketServer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final static Integer webSocketPort = 8088;

    @Autowired
    private NioWebSocketChannelInitializer channelInitializer;

    @Bean
    public void init() {
        logger.info("正在启动websocket服务器");
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(channelInitializer);
            Channel channel = bootstrap.bind(webSocketPort).sync().channel();
            logger.info("webSocket服务器启动成功：" + channel);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("运行出错：" + e);
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
            logger.info("websocket服务器已关闭");
        }
    }

//    public static void main(String[] args) {
//        new NioWebSocketServer().init(8088);
//    }
}

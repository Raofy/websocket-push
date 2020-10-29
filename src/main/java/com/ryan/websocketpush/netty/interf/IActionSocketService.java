package com.ryan.websocketpush.netty.interf;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author hongda.fang
 * @date 2019-11-08 16:08
 * ----------------------------------------------
 * 所有socket  bean 都需要继承
 *
 */
public interface IActionSocketService {

    Object doAction(ChannelHandlerContext context, String message);
}

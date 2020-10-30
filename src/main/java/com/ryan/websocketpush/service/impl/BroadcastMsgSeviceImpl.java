package com.ryan.websocketpush.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryan.websocketpush.annotation.ActionCode;
import com.ryan.websocketpush.annotation.SocketResponseBody;
import com.ryan.websocketpush.netty.constants.ActionCodeConstants;
import com.ryan.websocketpush.netty.global.ChannelSupervise;
import com.ryan.websocketpush.netty.interf.IActionSocketService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 广播消息
 *
 */
@Service
@Slf4j
@ActionCode(ActionCodeConstants.PUSH_MSG)
public class BroadcastMsgSeviceImpl implements IActionSocketService {


    @SocketResponseBody
    @Override
    public Object doAction(ChannelHandlerContext context, String message) {

        /**
         * 推送消息逻辑
         *
         */
        log.info("收到终端请求 === " + message);
        ChannelSupervise.removeChannelByActionCode(context.channel());
        ChannelSupervise.addChannel(context.channel());
        return "服务器广播消息";
    }
}

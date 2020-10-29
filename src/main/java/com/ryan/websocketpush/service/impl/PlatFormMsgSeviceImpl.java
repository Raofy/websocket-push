package com.ryan.websocketpush.service.impl;

import com.ryan.websocketpush.annotation.ActionCode;
import com.ryan.websocketpush.annotation.SocketResponseBody;
import com.ryan.websocketpush.netty.constants.ActionCodeConstants;
import com.ryan.websocketpush.netty.interf.IActionSocketService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Airey
 * @date 2019/12/27 15:27
 * ----------------------------------------------
 * TODO
 * ----------------------------------------------
 */
@Service
@Slf4j
@ActionCode(ActionCodeConstants.PLATFORM_MSG)
public class PlatFormMsgSeviceImpl implements IActionSocketService {


    @SocketResponseBody
    @Override
    public Object doAction(ChannelHandlerContext context, String message) {

        /**
         * 推送消息逻辑
         *
         */
        log.info("收到终端请求 === " + message);

        return "服务器端消息";
    }
}

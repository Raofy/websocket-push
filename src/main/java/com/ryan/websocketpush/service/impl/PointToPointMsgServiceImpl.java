package com.ryan.websocketpush.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryan.websocketpush.annotation.ActionCode;
import com.ryan.websocketpush.netty.constants.ActionCodeConstants;
import com.ryan.websocketpush.netty.global.ChannelSupervise;
import com.ryan.websocketpush.netty.interf.IActionSocketService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@ActionCode(ActionCodeConstants.SPECIAL_MSG)
public class PointToPointMsgServiceImpl implements IActionSocketService {
    @Override
    public Object doAction(ChannelHandlerContext context, String message) {
        log.info("收到终端请求 === " + message);
        JSONObject parseJson = JSONObject.parseObject(message);
        Integer actionCode = parseJson.getInteger("action");
        ChannelSupervise.removeChannel(context.channel());             //去除广播推送
        ChannelSupervise.addChannelByActionCode(context.channel(), actionCode);
        return "发送指定用户信息";
    }
}

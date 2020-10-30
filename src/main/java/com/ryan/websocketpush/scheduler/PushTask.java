package com.ryan.websocketpush.scheduler;

import com.ryan.websocketpush.netty.constants.ActionCodeConstants;
import com.ryan.websocketpush.netty.global.ChannelSupervise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 消息推送
 */
@Component
public class PushTask {

    @Scheduled(cron = "0/5 * * * * ? ")
    public void pushAll() {
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame("服务器5秒广播消息");
        ChannelSupervise.send2All(textWebSocketFrame);
    }

    @Scheduled(cron = "0/10 * * * * ? ")
    public void pushAllByActionCode() {
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame("服务器10秒指定用户消息");
        ChannelSupervise.send2AllAction(ActionCodeConstants.SPECIAL_MSG, textWebSocketFrame);
    }
}

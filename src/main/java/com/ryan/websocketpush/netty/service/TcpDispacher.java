package com.ryan.websocketpush.netty.service;

import cn.hutool.json.JSONUtil;

import com.ryan.websocketpush.bean.BaseResponse;
import com.ryan.websocketpush.netty.bean.BaseSocketRequest;
import com.ryan.websocketpush.netty.interf.IActionSocketService;
import com.ryan.websocketpush.utils.Constants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hongda.fang
 * @date 2019-11-08 16:05
 * ----------------------------------------------
 */
@Component
public class TcpDispacher {


    private Logger logger = LoggerFactory.getLogger(getClass());

    private static TcpDispacher instance = new TcpDispacher();

    private boolean isOK;

    private TcpDispacher() {

    }

    public static TcpDispacher getInstance() {
        return instance;
    }


    private static Map<Integer, Object> socketBeanTable = new ConcurrentHashMap<>();

    /**
     * 消息流转处理
     *
     * @param context
     * @param msg
     */
    public void messageRecived(ChannelHandlerContext context, String msg) {
        isOK = true;
        logger.info("收到socket:" + msg);
        if (!JSONUtil.isJson(msg)){
            errorResponse( " message 不符合 json 规范", context, msg);
        }
        BaseSocketRequest request = BaseSocketRequest.toBean(msg);
        if (isOK && request == null){
            errorResponse( " BaseSocketRequest is null",  context, msg);
        }

        if (!isOK){
            return;
        }

        Object socketService = socketBeanTable.get(request.getAction());
        if (socketService != null ){
            if(socketService instanceof IActionSocketService){
                IActionSocketService actionSocketService = (IActionSocketService)socketService;
                actionSocketService.doAction(context, msg);
            }else{
                errorResponse( "request is not instanceof BaseActionSocket",  context, msg);
            }
        }else{
            errorResponse( "没有找到对应的 socketBean， 请求的 socketBean 不能为 null",  context, msg);
        }
    }

    /**
     * 设置socketBean
     *
     * @param socketMap
     */
    public void setSocketBean(Map<Integer, Object> socketMap) {
        logger.warn("设置map的值");
        if (socketMap != null && socketMap.size() > 0) {
            for (Map.Entry<Integer, Object> entry : socketMap.entrySet()) {
                socketBeanTable.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 错误响应
     *
     * @param errMsg
     * @param context
     * @param msg
     */
    public void errorResponse(String errMsg, ChannelHandlerContext context, String msg){
        isOK = false;
        logger.error(Constants.LOGGER_STRING + errMsg);
        String s = JSONUtil.toJsonStr(BaseResponse.error(0, msg + ", " + errMsg));
        TextWebSocketFrame tws = new TextWebSocketFrame( s );
        context.channel().writeAndFlush(tws);
    }
}

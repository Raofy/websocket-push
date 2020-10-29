package com.ryan.websocketpush.netty.global;

import cn.hutool.core.collection.CollUtil;
import com.ryan.websocketpush.utils.JsonUtils2;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 频道管理
 */
public class ChannelSupervise {

    /**
     * 频道集群管理
     */
    private static ChannelGroup GlobalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 频道管理 【channel.id().asShortText()：channel.id()】
     */
    private static ConcurrentMap<String, ChannelId> ChannelMap = new ConcurrentHashMap();

    /**
     * （动作码：频道）映射
     */
    private static ConcurrentHashMap<Object, ChannelGroup> actionGlobalGroupMap = new ConcurrentHashMap<>();


    /**
     * 增加频道
     *
     * @param channel
     */
    public static void addChannel(Channel channel) {
        GlobalGroup.add(channel);
        ChannelMap.put(channel.id().asShortText(), channel.id());
    }

    /**
     * 移除频道
     *
     * @param channel
     */
    public static void removeChannel(Channel channel) {
        GlobalGroup.remove(channel);
        ChannelMap.remove(channel.id().asShortText());
        removeChannelByActionCode(channel);
    }

    /**
     * 查找频道
     *
     * @param id
     * @return
     */
    public static Channel findChannel(String id) {
        return GlobalGroup.find(ChannelMap.get(id));
    }

    /**
     * 推送消息
     *
     * @param tws
     */
    public static void send2All(TextWebSocketFrame tws) {
        GlobalGroup.writeAndFlush(tws);
    }


    public static void addChannelByActionCode(Channel channel, int actionCode) {
        ChannelGroup channels = actionGlobalGroupMap.get(actionCode);
        if (channels == null){
            channels =  new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        }
        channels.add(channel);
        actionGlobalGroupMap.put(actionCode, channels);
       // addChannelByObject(channel, actionCode);
    }


    public static void addChannelByObject(Channel channel, Object object) {
        ChannelGroup channels = actionGlobalGroupMap.get(object);
        if (channels == null){
            channels =  new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        }
        channels.add(channel);
        actionGlobalGroupMap.put(object, channels);
    }



    public static void removeChannelByActionCode(Channel channel){
        for (Object actionCode : actionGlobalGroupMap.keySet()){
            ChannelGroup channels = actionGlobalGroupMap.get(actionCode);
            removeChannel(channels, channel);
        }
    }

    private static void removeChannel(ChannelGroup channels , Channel channel){
        if (channels != null && channel != null){
            channels.remove(channel);
            channels.remove(channel.id().asShortText());
        }
    }


    public static void removeByObjectChannel(Channel channel, Object object){
        ChannelGroup channels = actionGlobalGroupMap.get(object);
        removeChannel(channels, channel);
    }


    /**
     * 发送到所有在线设备（通过动作码去推送消息）
     *
     * @param action
     * @param entity
     */
    public static void send2AllAction(Object action, Object entity) {
        ChannelGroup channels = actionGlobalGroupMap.get(action);
        if (channels != null){
            channels.writeAndFlush( new TextWebSocketFrame(JsonUtils2.writeValue(entity)));
        }
    }


    public static boolean existActionChannelGroup(int action){
        return CollUtil.isNotEmpty(actionGlobalGroupMap.get(action));
    }


    public static boolean existObjectChannelGroup(Object object){
        return CollUtil.isNotEmpty(actionGlobalGroupMap.get(object));
    }


}

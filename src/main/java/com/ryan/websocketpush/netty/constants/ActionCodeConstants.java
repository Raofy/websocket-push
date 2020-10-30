package com.ryan.websocketpush.netty.constants;

/**
 * socket消息状态码定义类
 * ----------------------------------------------
 */
public interface ActionCodeConstants {

    /**
     * 广播消息
     */
    int PUSH_MSG = 1024;

    /**
     * 指定用户消息
     */
    int SPECIAL_MSG = 1025;

}

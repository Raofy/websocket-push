package com.ryan.websocketpush.netty.constants;

/**
 * socket消息状态码定义类
 * ----------------------------------------------
 */
public interface ActionCodeConstants {

    /**
     * 其他类型消息
     */
    int PUSH_MSG = 1024;

    /**
     * 指定用户消息
     */
    int SPECIAL_MSG = 1025;

}

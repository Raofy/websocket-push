package com.ryan.websocketpush.annotation;

import java.lang.annotation.*;

/**
 * socket 返回 数据 注解
 * 自动解析为 json 并返回
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SocketResponseBody {

}

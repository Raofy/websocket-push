package com.ryan.websocketpush.annotation;

import java.lang.annotation.*;

/**
 *
 * socket 动作码 注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ActionCode {

    int value();
}

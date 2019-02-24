package com.lance.common.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD})
@Retention(RUNTIME)
public @interface RequestSign
{
    // 是否允许使用缓存
    boolean enableCache() default true;

    // 缓存持续时间(毫秒)
    int expire() default 3000;
}
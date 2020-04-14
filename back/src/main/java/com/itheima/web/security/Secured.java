package com.itheima.web.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})//表示该注解可以使用的位置
@Retention(RetentionPolicy.RUNTIME)//该注解保留的阶段
public @interface Secured {
    String value() default "user";
}

package com.liangquan.handw_spring.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// RUNTIME有效
@Retention(RetentionPolicy.RUNTIME)
// 表示该注解只能写在类上面
@Target(ElementType.TYPE)
public @interface ComponentScan {

    String value() default "";

}

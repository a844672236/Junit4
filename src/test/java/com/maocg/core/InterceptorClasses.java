package com.maocg.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface InterceptorClasses {
    //注解里面可以使用的类型 这里用于与接口 就是测试用例执行之前和执行之后的
    Class<?>[] value();
//    Class<?>[] ids() ;
}

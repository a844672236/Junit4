package com.maocg.core;

public interface Interceptor {

    //测试用例执行之后的方法
    void afterCase();

    void beforeCase();
}

package com.maocg.core;

public class Interceptorimpl implements Interceptor {
    //测试用例之前执行
    @Override
    public void beforeCase() {
        //  System.out.print("测试用例执行之前执行的方法");
    }

    //测试用例执行之后执行
    @Override
    public void afterCase() {
        //    System.out.print("测试用例执行之后执行的方法");
    }
}

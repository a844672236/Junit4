package com.maocg.core;

import com.maocg.entity.Assert;
import com.maocg.entity.Result;
import com.maocg.entity.Service;
import com.maocg.entity.TestCase;
import com.maocg.util.HttpUtils;
import org.junit.runners.model.Statement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCaseStatement extends Statement {

    //声明 Statement
    private Statement mStatement;
    //定义List 传过来
    private TestCase testCase ;
    //定义 map
    private Map<String, Service> serviceMap = new HashMap<>();

    //定义一个列表
    private List<Interceptor> interceptorList = new ArrayList<Interceptor>(1);

    //构造函数
    public TestCaseStatement(Statement mStatement,TestCase testCase,Map<String, Service> serviceMap){
        this.mStatement = mStatement;
        this.testCase = testCase;
        this.serviceMap = serviceMap;

    }


    //测试用例真正执行的地方
    @Override
    public void evaluate() throws Throwable {

        //测试用例执行之前执行
        //使用for循环
        for (Interceptor interceptor:interceptorList) {

            interceptor.beforeCase();
        }

        //执行测试用例
        mStatement.evaluate();

        //进行网络请求 同时对返回数据进行断言
        HtppCase();

        //使用for 循环
        for (Interceptor interceptor:interceptorList){

            interceptor.afterCase();
        }

        //测试用例执行之后执行
    }

    //添加主机进来的东西
    public void addInterceptor(Interceptor interceptor){

        interceptorList.add(interceptor);
    }

    //定义网络请求
    public void HtppCase(){

        Service service = serviceMap.get(testCase.getCall().getService());

        String url = service.getUrl();

        Map<String,String> map = testCase.getCall().getMap();

        String method = service.getMethod();

        //打印请求参数
        System.out.println(testCase.getId()+"请求URL是:"+service.getUrl());
        System.out.println(testCase.getId()+"请求参数是:"+map.toString());
        System.out.println(testCase.getId()+"请求方法是:"+service.getMethod());

        //调用封装的网络请求
        String json = HttpUtils.HttpFramework(url,map,method);

        //打印返回结果
        System.out.println(testCase.getId()+"服务器放回到结果是"+json);

        //进行断言
        //获取 Assert 对象
        Assert mAssert = testCase.getAnAssert();

        //判断为空不为空 null
        if (null == mAssert){

            System.out.print("没有Assert标签,请添加Assert标签进行断言");

            return;
        }

        //获取 result 对象
//        Result mResult = mAssert.getmResult();
          Result mResult = (Result) mAssert.getmResult();

        //判断空
        if (null == mResult){
            System.out.print("没有result标签,请添加result标签进行断言");
            return;
        }

        //获取内容进行断言
        //判断两个json 是否一样
        if (json.equals(mResult.getJsonRsult())){

            //如果 期望值 和 返回值是相同的设置为 true
            assert true;
        }else {
            System.out.println("期望值是:"+mResult.getJsonRsult());
            System.out.println("实际值是:"+json);
            //如果 期望值 和 返回值是不相同的设置为 false
            assert false;
        }



    }

}

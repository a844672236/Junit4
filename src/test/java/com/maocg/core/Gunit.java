package com.maocg.core;

import com.maocg.entity.Service;
import com.maocg.entity.TestCase;
import com.maocg.saxxml.SaxHandler;
import com.maocg.saxxml.SaxHostHandler;
import com.maocg.util.FileUtils;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gunit extends ParentRunner<TestSuitRunner> {
    //定义一个List 类型 用来装测试用例
    private List<TestSuitRunner> testSuitRunnerList = new ArrayList<TestSuitRunner>();
    //测试用例用例表
    private List<TestCase> testCaseList = new ArrayList<>();
    //声明HOST列表
    private List<Service> serviceList = new ArrayList<>();
    //声明Map 集合
    private Map<String,Service> serviceMap =new HashMap<>();

    /**
     * Constructs a new {@code ParentRunner} that will run {@code @TestClass}
     *
     * @param testClass
     */
    public Gunit(Class<?> testClass) throws InitializationError {
        super(testClass);
        //调用获取文件的方法
        getAllFileXml();

        //调用解析 HOST
        getServiceFile();
        InterceptorClasses annotation = testClass.getAnnotation(InterceptorClasses.class);
        for (Class<?> id : annotation.ids()) {

        }
        //for 循环执行次 数
        for (int i = 0; i < testCaseList.size(); i++) {
            TestSuitRunner testSuitRunner = new TestSuitRunner(testClass,testCaseList.get(i),serviceMap);
            testSuitRunnerList.add(testSuitRunner);

        }
//        TestSuitRunner testSuitRunner = new TestSuitRunner(testClass);
//        testSuitRunnerList.add(testSuitRunner);

    }

    @Override
    protected List<TestSuitRunner> getChildren() {
        return testSuitRunnerList;
    }

    @Override
    protected Description describeChild(TestSuitRunner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(TestSuitRunner child, RunNotifier notifier) {
        child.run(notifier);
    }

    //获取 Service 方法
    public void getServiceFile(){
        //获取当前 case 路径
        String path = getClass().getClassLoader().getResource("service").getPath();
        //对下面文件进行遍历，获取所有的xml文件
        List<File> fileList = FileUtils.getFileList(path);

        //使用for循环遍历
        for (File file : fileList){
            saxServiceXml(file.toString());
        }
    }



    //获取路径方法
    public void getAllFileXml(){
        //获取当前 case 路径
        String path = getClass().getClassLoader().getResource("case").getPath();
        //对下面文件进行遍历，获取所有的xml文件
        List<File> fileList = FileUtils.getFileList(path);

        for (File file : fileList) {
            saxXml(file.toString());
        }
    }

    //封装解析 service xml 的方法
    public void saxServiceXml(String path){
        //解析数据
        //创建解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();

        //获取  SAXparser
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            //实例化自定义
            SaxHostHandler handler = new SaxHostHandler();
            //进行解析
            parser.parse(path,handler);
            //每个xml 里面所有的测试用例加入到 List里面
            serviceList.addAll(handler.getServiceList());

            //将List 列表转为map集合，key 就是 id
            for (Service service : serviceList) {
                serviceMap.put(service.getId(),service);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //封装xml解析方法
    public void saxXml(String path){

        //解析数据
        //创建解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();

        //获取  SAXparser
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            //实例化自定义
            SaxHandler handler = new SaxHandler();
            //进行解析
            parser.parse(path,handler);
            //每个xml 里面所有的测试用例加入到 List里面
            testCaseList.addAll(handler.getTestCasesList());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}



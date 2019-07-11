package com.maocg.saxxml;

import com.maocg.entity.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SaxHostHandler extends DefaultHandler {
    //声明列表
    private List<Service> serviceList = new ArrayList<>();
    //声明对象
    Service mService;
    //提供对外访问的方法
    public List<Service> getServiceList(){

        return serviceList;
    }


    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);


        if (qName.equals("HTTP")){

            mService = new Service();

            //获取属性个数
            int number = attributes.getLength();

            //使用for 循环
            for (int i = 0; i < number; i++) {

                if (attributes.getQName(i).equals("id")){

                    //获取值进行设置
                    mService.setId(attributes.getValue(i));
                }

                if (attributes.getQName(i).equals("method")){

                    //获取值进行设置
                    mService.setMethod(attributes.getValue(i));
                }

                if (attributes.getQName(i).equals("url")){

                    //获取值进行设置
                    mService.setUrl(attributes.getValue(i));
                }

                if (attributes.getQName(i).equals("desc")){

                    //获取值进行设置
                    mService.setDesc(attributes.getValue(i));
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        //结束遍历的时候将对象加入到列表里面
        if (qName.equals("HTTP")){
            serviceList.add(mService);
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

    }


}



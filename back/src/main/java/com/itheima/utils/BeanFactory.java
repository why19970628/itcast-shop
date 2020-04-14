package com.itheima.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;

public class BeanFactory {
    //创建map集合用来存储   根据xml解析出来的接口名对应的实现类对象
   private static HashMap<String,Object> map =  new HashMap<String,Object>();
    static{
        try {
            //解析xml文件

            //创建解析器对象
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
            //获取所有bean标签
            List<Element> list = document.selectNodes("//bean");
            //遍历list集合  拿到每一个bean标签
            for (Element element : list) {
                //获取id属性值
                String id = element.attributeValue("id");


                //获取class属性值
                String aClass = element.attributeValue("class");
                //创建类对象
                Class<?> aClass1 = Class.forName(aClass);
                //创建实现类对象
                Object o = aClass1.newInstance();

                //将获取接口名   和    实现类对象  存入map集合
                map.put(id,o);
            }

        } catch (Exception e) {
           throw new RuntimeException(e);
        }

    }
    public static <T> T getBean(Class<T> clazz){
           //获取接口的名字
        String simpleName = clazz.getSimpleName();
        return (T) map.get(simpleName);

    }
}

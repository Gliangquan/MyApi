package org.quan.spring5;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Field;
import java.util.Map;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        // xml注入 Bean
        ApplicationContext context_xml = new ClassPathXmlApplicationContext("applicationContext.xml");
        Component2 component2_xml = (Component2) context_xml.getBean("component2");
        System.out.println("component2_xml = " + component2_xml);

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Component2 component2 = (Component2) context.getBean("component2");
        System.out.println("component2 = " + component2);

        /*  *
         * @Title: 1.1 什么是 BeanFactory
         * 它是 ApplicationContext 的父接口
         * 它才是 Spring 的核心容器，主要的 ApplicationContext 实现 组合 了它的功能，也就是说，BeanFactory 是 ApplicationContext 中的一个成员变量。
         * 常用的 context.getBean("xxx") 方法，其实是调用了 BeanFactory 的 getBean() 方法。
         */
//        Field singletonObjects = null;
//        try {
//            singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
//            singletonObjects.setAccessible(true);
//            ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
//            // 获取 BeanFactory 的属性
//            Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
//            map.entrySet().stream().filter(e -> e.getKey().startsWith("component"))
//                    .forEach(e -> System.out.println(e.getKey() + "=" + e.getValue()));
//
//            context.close();
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }


    }
}

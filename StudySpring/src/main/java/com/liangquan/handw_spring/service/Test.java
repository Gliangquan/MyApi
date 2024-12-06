package com.liangquan.handw_spring.service;

import com.liangquan.handw_spring.spring.JcenApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName：Test
 * @Author: liangquan
 * @Date: 2024/12/6 14:25
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context就是spring容器

//        使用spring容器的时候，传入 配置文件 或者 配置类（AppConfig）
        JcenApplicationContext jcenApplicationContext = new JcenApplicationContext(AppConfig.class);

        System.out.println(jcenApplicationContext.getBean("userService"));
//        System.out.println(jcenApplicationContext.getBean("userService"));
//        System.out.println(jcenApplicationContext.getBean("userService"));
//        System.out.println(jcenApplicationContext.getBean("userService"));

//        代理对象代理的是接口，这里强转为UserService就会报错
//        UserService userService = (UserService) jcenApplicationContext.getBean("userService");

        UserInterface userService = (UserInterface) jcenApplicationContext.getBean("userService");

        userService.test();

    }
}

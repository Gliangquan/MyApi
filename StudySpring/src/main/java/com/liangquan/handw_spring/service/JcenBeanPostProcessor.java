package com.liangquan.handw_spring.service;

import com.liangquan.handw_spring.spring.BeanPostProcessor;
import com.liangquan.handw_spring.spring.Component;
import com.liangquan.handw_spring.spring.JcenApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName：JcenBeanPostProcessor
 * @Author: liangquan
 * @Date: 2024/12/6 18:45
 * @Description: JcenBeanPostProcessor
 */

@Component
public class JcenBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
//        System.out.println("bean 创建前");
        if (beanName.equals("userService")) {
            System.out.println("创建UserService前 = " + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
//        System.out.println("bean 创建后");
        if (beanName.equals("userService")){
            Object proxyInstance = Proxy.newProxyInstance(JcenApplicationContext.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("切面逻辑");
                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }
        return bean;
    }

}

package com.liangquan.handw_spring.service;

import com.liangquan.handw_spring.spring.Autowired;
import com.liangquan.handw_spring.spring.BeanNameAware;
import com.liangquan.handw_spring.spring.Component;
import com.liangquan.handw_spring.spring.InitializingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;

/**
 * @ClassName：UserService
 * @Author: liangquan
 * @Date: 2024/12/6 14:25
 * @Description: Service
 */

// 指定Bean名字
@Component("userService")
// bean 单例还是多例 (不写or(singleton)是单例)
 @Scope("prototype")
public class UserService implements BeanNameAware, InitializingBean, UserInterface{

    // 依赖注入
    @Autowired()
    private OrderService orderService;

    private String beanName;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void isOrderService(){
        System.out.println("orderService = " + orderService);
    }

    @Override
    public void afterPropertiesSet() {
        // 初始化操作
//        System.out.println("初始化操作");
    }

    @Override
    public void test() {
        System.out.println("userService test function");
    }
}

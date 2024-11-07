package com.liangquan.controller;

import com.liangquan.dubbo.service.TestDubboService;
import com.liangquan.entity.Users;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("dubbo")
public class TestDubboController {

//    @DubboReference()
    @DubboReference(version = "1.0.0")
    private TestDubboService testDubboService;

    @GetMapping("hello")
    public String hello(String name) {
        return testDubboService.sayHello(name);
    }


    @GetMapping("getUsers")
    public Users getUsers() {
        return testDubboService.getUsers();
    }
}

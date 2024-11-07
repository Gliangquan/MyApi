package com.liangquan.dubbo.service;

import com.liangquan.entity.Users;

public interface TestDubboService {

    String sayHello(String name);

    Users getUsers();
}

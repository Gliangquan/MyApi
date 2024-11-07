package com.liangquan.service.dubbo;

import com.liangquan.dubbo.service.TestDubboService;
import org.apache.dubbo.config.annotation.DubboService;
import com.liangquan.entity.Users;

@DubboService(version = "1.0.0", timeout = 60000)
public class TestDubboServiceImpl implements TestDubboService {

    @Override
    public String sayHello(String name) {
        return "hello," + name;
    }

    @Override
    public Users getUsers() {
        Users users = new Users();
        users.setUserName("Leung")
                .setUserAge(20);
        return users;
    }
}

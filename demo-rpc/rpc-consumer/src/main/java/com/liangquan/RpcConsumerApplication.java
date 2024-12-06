package com.liangquan;

import com.liangquan.consumer.UserServiceProxy;
import com.liangquan.model.Users;
import com.liangquan.proxy.ServiceProxyFactory;
import com.liangquan.service.UsersService;

/**
 * rpc消费者应用程序
 */
public class RpcConsumerApplication {
    public static void main( String[] args ) {
        // todo 需要获取 UserService 的实现类对象
        // 静态代理
//        UsersService usersService = new UserServiceProxy();
        // 动态代理
         UsersService usersService = ServiceProxyFactory.getProxy(UsersService.class);

        Users user = new Users();
        user.setName("jcen");
        // 调用
        Users newUser = usersService.getUsers(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}


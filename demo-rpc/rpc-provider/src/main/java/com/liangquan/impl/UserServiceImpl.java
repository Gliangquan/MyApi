package com.liangquan.impl;

import com.liangquan.model.Users;
import com.liangquan.service.UsersService;

/**
 * 用户服务实现类
 */
public class UserServiceImpl implements UsersService {

    @Override
    public Users getUsers(Users users) {
        System.out.println("用户名：" + users.getName());
        return users;
    }
}

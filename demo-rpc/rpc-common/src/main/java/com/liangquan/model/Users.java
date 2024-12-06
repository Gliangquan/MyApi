package com.liangquan.model;

import java.io.Serializable;

/**
 * @ClassName：Users
 * @Author: liangquan
 * @Date: 2024/11/9 14:36
 * @Description: Users对象要实现序列化，为后续网络传输提供支持
 */
public class Users implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}




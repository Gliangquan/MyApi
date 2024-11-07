package com.liangquan.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Data
@Accessors(chain =true)
public class Users implements Serializable {

    // 需要实现序列化

    private String userName;

    private int userAge;
}

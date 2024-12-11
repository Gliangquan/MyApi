package com.liangquan.handw_spring.spring;

/**
 * @ClassName：BeanDifinition
 * @Author: liangquan
 * @Date: 2024/12/6 17:30
 * @Description: BeanDefinition对象
 */
public class BeanDefinition {

    private Class Type;

//    单例还是多例
    private String scope;


    public Class getType() {
        return Type;
    }

    public void setType(Class type) {
        Type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

package org.quan.spring5;

import org.springframework.stereotype.Component;

@Component
public class Component2 {

    private Component1 component1;

    public void setComponent1(Component1 component1){
        this.component1 = component1;
    }

}
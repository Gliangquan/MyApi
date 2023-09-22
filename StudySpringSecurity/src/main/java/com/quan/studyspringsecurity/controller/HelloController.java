package com.quan.studyspringsecurity.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloController {

    @GetMapping("hello")
    public String getHello(){
        return "hello getHello";
    }

    @GetMapping("index")
    public String getIndex(){
        return "hello getIndex";
    }
}

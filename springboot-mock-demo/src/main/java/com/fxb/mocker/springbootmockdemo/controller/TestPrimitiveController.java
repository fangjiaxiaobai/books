package com.fxb.mocker.springbootmockdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fxb.mocker.springbootmockdemo.feign.TestPrimitiveFeign;

/**
 * 测试基本类型的controller
 *
 * @author fangjiaxiaobai
 * @date 2021-02-18 16:28
 * @since 1.0.0
 */
@RestController
public class TestPrimitiveController {

    private TestPrimitiveFeign feign;

    public TestPrimitiveController(TestPrimitiveFeign feign){
        this.feign = feign;
    }

    @GetMapping("v1")
    public Object v1(){
        return this.feign.v1();
    }
    
    @GetMapping("v2")
    public Object v2(){
        return this.feign.v2();
    }
    @GetMapping("v3")
    public Object v3(){
        return this.feign.v3();
    }
    @GetMapping("v4")
    public Object v4(){
        return this.feign.v4();
    }

}

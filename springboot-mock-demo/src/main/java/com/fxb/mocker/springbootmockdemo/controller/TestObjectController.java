package com.fxb.mocker.springbootmockdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fxb.mocker.springbootmockdemo.feign.TestObjectFeign;
import com.fxb.mocker.springbootmockdemo.feign.TestPrimitiveFeign;

/**
 * 测试基本类型的controller
 *
 * @author fangjiaxiaobai
 * @date 2021-02-18 16:28
 * @since 1.0.0
 */
@RestController
@RequestMapping("obj")
public class TestObjectController {

    private TestObjectFeign feign;

    public TestObjectController(TestObjectFeign feign) {
        this.feign = feign;
    }

    @GetMapping("v1")
    public Object v1() {
        return this.feign.v1();
    }

    @GetMapping("v2")
    public Object v2() {
        return this.feign.v2();
    }

    @GetMapping("v3")
    public Object v3() {
        return this.feign.v3();
    }

}

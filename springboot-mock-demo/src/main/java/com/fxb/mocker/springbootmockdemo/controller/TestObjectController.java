package com.fxb.mocker.springbootmockdemo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fxb.mocker.springbootmockdemo.feign.TestObjectFeign;
import com.fxb.mocker.springbootmockdemo.feign.TestPrimitiveFeign;
import com.fxb.mocker.springbootmockdemo.vo.FinalFieldObject;
import com.fxb.mocker.springbootmockdemo.vo.RestResponse;
import com.fxb.mocker.springbootmockdemo.vo.SimpleObject;
import com.fxb.mocker.springbootmockdemo.vo.speficed.FirstPageDataResponse;

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

    private ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

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

    @GetMapping("v5")
    public Object v5() {
        return this.feign.v5();
    }

    @GetMapping("v6")
    public Object v6() {
        return this.feign.v6();
    }

    @GetMapping("v7")
    public Object v7() {
        return this.feign.v7();
    }

    @GetMapping("v8")
    public Object v8() {
        FinalFieldObject<SimpleObject> simpleObjectFinalFieldObject = this.feign.v8();
        System.out.println(simpleObjectFinalFieldObject);
        return simpleObjectFinalFieldObject;
    }
    @GetMapping("v9")
    public Object v9() throws JsonProcessingException {
        FinalFieldObject<FirstPageDataResponse> firstPageDataResponseFinalFieldObject = this.feign.v9();
        String x = objectMapper.writeValueAsString(firstPageDataResponseFinalFieldObject);
        System.out.println(x);
        return x;
    }

    @GetMapping("v10")
    public Object v10() throws JsonProcessingException {
        FinalFieldObject<List<SimpleObject>> listFinalFieldObject = this.feign.v10();
        String x = objectMapper.writeValueAsString(listFinalFieldObject);
        System.out.println(x);
        return x;
    }
    @GetMapping("v11")
    public Object v11() throws JsonProcessingException {
        RestResponse<List<SimpleObject>> listFinalFieldObject = this.feign.v11();
        String x = objectMapper.writeValueAsString(listFinalFieldObject);
        System.out.println(x);
        return x;
    }

}

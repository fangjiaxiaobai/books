package com.fxb.mocker.springbootmockdemo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.fxb.mocker.springbootmockdemo.feign.fallback.TestObjectFeignFallBack;
import com.fxb.mocker.springbootmockdemo.vo.SimpleObject;

/**
 * 测试返回参数 为Object的类型
 *
 * @author fangjiaxiaobai
 * @date 2021-02-18 16:15
 * @since 1.0.0
 */
@FeignClient(name = "EDU-B-CRM.GAOTU100.COM", path = "edu/crm", fallbackFactory = TestObjectFeignFallBack.class)
public interface TestObjectFeign {

    @PostMapping("so/v1")
    SimpleObject v1();


    @PostMapping("list/v1")
    List<SimpleObject> v2();


    @PostMapping("list/v2")
    List<String> v3();

}

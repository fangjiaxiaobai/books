package com.fxb.mocker.springbootmockdemo.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.fxb.mocker.annotation.Mocker;
import com.fxb.mocker.springbootmockdemo.feign.fallback.TestPrimitiveFeignFallBack;

/**
 * 测试基本类型
 *
 * @author fangjiaxiaobai
 * @date 2021-02-18 16:22
 * @since 1.0.0
 */
@FeignClient(name = "EDU-B-CRM.GAOTU100.COM", path = "edu/crm", fallbackFactory = TestPrimitiveFeignFallBack.class)
public interface TestPrimitiveFeign {

    @PostMapping("v1")
    @Mocker(autoGen = false)
    String v1();


    @PostMapping("v2")
    @Mocker(autoGen = false)
    byte v2();


    @PostMapping("v3")
    @Mocker(autoGen = false)
    Integer v3();


    @PostMapping("v4")
    @Mocker(autoGen = false)
    int v4();

}

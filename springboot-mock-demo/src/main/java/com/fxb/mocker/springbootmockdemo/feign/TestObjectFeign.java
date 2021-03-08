package com.fxb.mocker.springbootmockdemo.feign;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.fxb.mocker.annotation.Mocker;
import com.fxb.mocker.springbootmockdemo.feign.fallback.TestObjectFeignFallBack;
import com.fxb.mocker.springbootmockdemo.vo.FinalFieldObject;
import com.fxb.mocker.springbootmockdemo.vo.NestedObject;
import com.fxb.mocker.springbootmockdemo.vo.RestResponse;
import com.fxb.mocker.springbootmockdemo.vo.SimpleObject;
import com.fxb.mocker.springbootmockdemo.vo.speficed.FirstPageDataResponse;
import com.fxb.mocker.springbootmockdemo.vo.speficed.SubclazzLessonDataResponse;

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
    @Mocker
    SimpleObject v1();


    @PostMapping("list/v1")
    @Mocker
    List<SimpleObject> v2();


    @PostMapping("list/v2")
    @Mocker
    List<String> v3();

    @PostMapping("no/v5")
    @Mocker
    NestedObject v5();

    @PostMapping("no/v6")
    @Mocker
    Set<NestedObject> v6();

    @PostMapping("fo/v7")
    @Mocker
    FinalFieldObject<String> v7();

    @PostMapping("fo/v8")
    @Mocker
    FinalFieldObject<SimpleObject> v8();

    @PostMapping("fo/v9")
    @Mocker
    FinalFieldObject<FirstPageDataResponse> v9();

    @PostMapping("fo/v10")
    @Mocker
    FinalFieldObject<List<SimpleObject>> v10();

    @PostMapping("fo/v11")
    @Mocker
    RestResponse<List<SimpleObject>> v11();
}

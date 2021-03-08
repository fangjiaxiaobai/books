package com.fxb.mocker.springbootmockdemo.feign.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fxb.mocker.springbootmockdemo.feign.TestObjectFeign;
import com.fxb.mocker.springbootmockdemo.vo.SimpleObject;

import feign.hystrix.FallbackFactory;

/**
 * @author fangjiaxiaobai
 * @date 2021-02-18 16:17
 * @since 1.0.0
 */
@Component
public class TestObjectFeignFallBack implements FallbackFactory<TestObjectFeign> {

    public TestObjectFeign create(Throwable throwable) {
        return new TestObjectFeign() {
            public SimpleObject v1() {
                return null;
            }

            public List<SimpleObject> v2() {
                return null;
            }

            public List<String> v3() {
                return null;
            }
        };
    }
}

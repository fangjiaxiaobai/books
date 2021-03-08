package com.fxb.mocker.springbootmockdemo.feign.fallback;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fxb.mocker.springbootmockdemo.feign.TestObjectFeign;
import com.fxb.mocker.springbootmockdemo.vo.FinalFieldObject;
import com.fxb.mocker.springbootmockdemo.vo.NestedObject;
import com.fxb.mocker.springbootmockdemo.vo.RestResponse;
import com.fxb.mocker.springbootmockdemo.vo.SimpleObject;
import com.fxb.mocker.springbootmockdemo.vo.speficed.FirstPageDataResponse;

import feign.hystrix.FallbackFactory;

/**
 * @author fangjiaxiaobai
 * @date 2021-02-18 16:17
 * @since 1.0.0
 */
@Component
public class TestObjectFeignFallBack implements FallbackFactory<TestObjectFeign> {

    @Override
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

            public NestedObject v5() {
                return null;
            }

            public Set<NestedObject> v6() {
                return null;
            }

            public FinalFieldObject<String> v7() {
                return null;
            }

            public FinalFieldObject<SimpleObject> v8() {
                return null;
            }

            public FinalFieldObject<FirstPageDataResponse> v9() {
                return null;
            }

            public FinalFieldObject<List<SimpleObject>> v10() {
                return null;
            }

            @Override
            public RestResponse<List<SimpleObject>> v11() {
                return null;
            }
        };
    }
}

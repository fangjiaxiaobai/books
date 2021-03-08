package com.fxb.mocker.springbootmockdemo.feign.fallback;

import org.springframework.stereotype.Component;

import com.fxb.mocker.springbootmockdemo.feign.TestObjectFeign;
import com.fxb.mocker.springbootmockdemo.feign.TestPrimitiveFeign;

import feign.hystrix.FallbackFactory;

/**
 * 测试基本类型
 *
 * @author fangjiaxiaobai
 * @date 2021-02-18 16:17
 * @since 1.0.0
 */
@Component
public class TestPrimitiveFeignFallBack implements FallbackFactory<TestPrimitiveFeign> {

    public TestPrimitiveFeign create(Throwable throwable) {
        return new TestPrimitiveFeign() {
            public String v1() {
                return null;
            }

            public byte v2() {
                return 0;
            }

            public Integer v3() {
                return null;
            }

            public int v4() {
                return 0;
            }
        };
    }
}

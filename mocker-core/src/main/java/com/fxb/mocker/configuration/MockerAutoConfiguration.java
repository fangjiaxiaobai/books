package com.fxb.mocker.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import feign.Feign;
import feign.MockerReflectiveFeign;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

/**
 * Mocker 配置
 *
 * @author fangjiaxiaobai
 * @date 2021-02-18 16:44
 * @since 1.0.0
 */
@Configuration
@Profile( { "test","dev" })
@Slf4j
public class MockerAutoConfiguration {

    public MockerAutoConfiguration() {
        log.info("----------- MockerAutoConfiguration ---------------");
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public Feign.Builder feignBuilder(Retryer retryer) {
        return MockerReflectiveFeign.builder().retryer(retryer);
    }

    @Bean
    @ConditionalOnMissingBean
    public Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }


}

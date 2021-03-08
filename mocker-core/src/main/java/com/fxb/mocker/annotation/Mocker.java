package com.fxb.mocker.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mocker 核心注解
 * <p>
 * 用于描述某个类或者某个方法使用Mocker数据
 *
 * @author fangxiaobai
 * @date 2021-02-18 15:17
 * @since 1.0.0
 */
@Documented
@Target(value = { ElementType.METHOD, ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Mocker {

    /**
     * @return
     */
    String value() default "";

    /**
     * 是否自动生成
     *
     * @return
     */
    boolean autoGen() default true;

    /**
     * 文件地址
     *
     * @return 文件地址
     */
    String path() default "";

}

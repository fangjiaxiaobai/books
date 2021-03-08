package com.fxb.mocker.utils.model;

import java.lang.reflect.Method;

import com.fxb.mocker.utils.bean.TypeEnum;

/**
 * @author fangjiaxiaobai
 * @date 2021-02-26 16:52
 * @since 1.0.0
 */
public interface INewBeanModel {

    /**
     * 获取类
     *
     * @return
     */
    Class<?> getClazz();

    /**
     * 获取方法
     *
     * @return
     */
    Method getMethod();

    /**
     * 获取对象的类型
     *
     * @return
     */
    TypeEnum getType();

}

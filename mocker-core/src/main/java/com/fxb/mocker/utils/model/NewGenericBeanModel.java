package com.fxb.mocker.utils.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.fxb.mocker.utils.bean.TypeEnum;

/**
 * 创建泛型对象
 *
 * @author fangjiaxiaobai
 * @date 2021-02-26 16:52
 * @since 1.0.0
 */
public class NewGenericBeanModel extends NewBeanModel {

    Class<?> clazz;

    Class<?> genericClazz;

    public NewGenericBeanModel(Method method, TypeEnum typeEnum, Class<?> genericClazz, Class<?> clazz) {
        super(method);
        this.clazz = clazz;
        this.genericClazz = genericClazz;
        this.type = typeEnum;
    }
    public Class<?> getGenericClazz() {
        return genericClazz;
    }

    @Override
    public Class<?> getClazz() {
        return this.clazz;
    }

    @Override
    public Method getMethod() {
        return super.getMethod();
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    @Override
    protected void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}

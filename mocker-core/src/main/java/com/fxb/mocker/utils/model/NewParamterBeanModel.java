package com.fxb.mocker.utils.model;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.fxb.mocker.utils.bean.TypeEnum;

/**
 * 创建泛型对象
 *
 * @author fangjiaxiaobai
 * @date 2021-02-26 16:52
 * @since 1.0.0
 */
public class NewParamterBeanModel extends NewBeanModel {

    private Class<?> clazz;

    private Parameter parameter;

    public NewParamterBeanModel(Method method, TypeEnum typeEnum, Parameter parameter, Class<?> clazz) {
        super(method);
        this.clazz = clazz;
        this.parameter = parameter;
        this.type = typeEnum;
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

    public Parameter getParameter() {
        return parameter;
    }
}

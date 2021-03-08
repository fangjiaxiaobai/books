package com.fxb.mocker.utils.model;

import java.lang.reflect.Method;

import com.fxb.mocker.utils.bean.TypeEnum;

/**
 * @author fangjiaxiaobai
 * @date 2021-02-26 16:52
 * @since 1.0.0
 */
public class NewBeanModel implements INewBeanModel {

    /**
     * 父类方法名
     */
    private final Method method;

    protected TypeEnum type;

    protected Class<?> clazz;

    public NewBeanModel(Method method) {
        this.method = method;
        this.type = TypeEnum.Method;
        this.clazz = method.getReturnType();
    }

    @Override
    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public TypeEnum getType() {
        return type;
    }

    protected void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }


}

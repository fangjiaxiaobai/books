package com.fxb.mocker.utils.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.fxb.mocker.utils.bean.NewBeanUtil;
import com.fxb.mocker.utils.bean.TypeEnum;

/**
 * 创建字段对象
 *
 * @author fangjiaxiaobai
 * @date 2021-02-26 16:52
 * @since 1.0.0
 */
public class NewFieldBeanModel extends NewBeanModel {

    Field field;

    Class<?> clazz;

    public NewFieldBeanModel(Method method, TypeEnum typeEnum, Field field, Class<?> clazz) {
        super(method);
        this.field = field;
        this.clazz = clazz;
        this.type = typeEnum;
    }



    public Field getField() {
        return field;
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

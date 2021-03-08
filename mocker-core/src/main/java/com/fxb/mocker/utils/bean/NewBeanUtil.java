package com.fxb.mocker.utils.bean;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

import com.fxb.mocker.utils.random.RandomUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建Bean util
 *
 * @author fangjiaxiaobai
 * @date 2021-02-20 12:20
 * @since 1.0.0
 */
@Slf4j
public class NewBeanUtil {

    /**
     * 创建对象
     *
     * @param tClass 对象的类型
     * @param <T>    类型
     *
     * @return 对象
     */
    public static <T> T newInstance(
            Class<T> tClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        // 判断是否为数组
        boolean isArray = tClass.isArray();
        if (isArray) {
            System.out.println("isArray");
            return doParseArray(tClass);
        }
        // 判断是否为集合
        else if (Collection.class.isAssignableFrom(tClass)) {
            System.out.println("isCollection");
            return doParseCollection(tClass);
        } else if (Map.class.isAssignableFrom(tClass)) {
            System.out.println("isCollection");
            return doParseMap(tClass);
        }
        // 判断是否为接口
        else if (tClass.isInterface()) {
            System.out.println("isInterface");
            return doParseInterface(tClass);
        }
        // 判断是否为简单类型:(基本类型,包装类型,时间类型,String)
        if (BeanTypeUtil.isSimpleField(tClass)) {
            return (T) BeanTypeUtil.getSimpleFieldValue(tClass);
        } else {
            // 如果是自定义类,则获取是否有父类
            T t = tClass.newInstance();
            // 获取类的字段
            Field[] declaredFields = tClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                // 判断字段的类型。基本类型,包装类型,String,Date,自定义类型,
                Object instance = newInstance(declaredField.getType());
                ReflectionUtils.makeAccessible(declaredField);
                ReflectionUtils.setField(declaredField, t, instance);
                // 解析嵌套类型,判断字段类型。
            }
            return t;
        }
    }

    /**
     * 解析接口
     *
     * @param tClass 目标类
     * @param <T>    指定类型T
     *
     * @return t
     */
    private static <T> T doParseInterface(Class<T> tClass) {
        return null;
    }

    /**
     * 解析Map
     *
     * @param tClass 目标类
     * @param <T>    指定类型T
     *
     * @return T
     */
    private static <T> T doParseMap(Class<T> tClass) {
        log.error("不支持Map,暂且给您返回null了");
        return null;
    }

    /**
     * 解析集合
     *
     * @param tClass 目标类
     * @param <T>    指定类型T
     *
     * @return T
     */
    private static <T> T doParseCollection(Class<T> tClass) {
        System.out.println(tClass.getCanonicalName());
        String canonicalName = tClass.getCanonicalName();

        TypeVariable<Class<T>>[] typeParameters = tClass.getTypeParameters();
        if(typeParameters.length == 0){
            log.error("返回参数为List, 但未指定泛型");
            return null;
        }

        TypeVariable<Class<T>> typeParameter = typeParameters[0];


        if ("java.lang.ArrayList".equals(canonicalName)) {

        } else if ("java.lang.LinkedList".equals(canonicalName)) {

        } else if ("java.lang.List".equals(canonicalName)) {

        }
        return null;
    }

    /**
     * 解析数组类型
     *
     * @param tClass 目标类
     * @param <T>    指定类型T
     *
     * @return T
     */
    private static <T> T doParseArray(
            Class<T> tClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String canonicalName = tClass.getCanonicalName();
        if (canonicalName.endsWith("[][]")) {
            throw new RuntimeException("目前不支持多维数据");
        }

        String className = canonicalName.replace("[]", "");
        Class<?> aClass = Class.forName(className);
        int length = RandomUtil.randomIntInTen();
        Object array = Array.newInstance(aClass, length);
        for (int i = 0; i < length; i++) {
            Array.set(array, i, newInstance(aClass));
        }
        return (T) array;
    }

}

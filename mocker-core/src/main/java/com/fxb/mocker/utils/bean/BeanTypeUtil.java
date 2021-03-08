package com.fxb.mocker.utils.bean;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fxb.mocker.utils.random.RandomUtil;

/**
 * bean 类型 工具类
 *
 * @author fangjiaxiaobai
 * @date 2021-02-20 12:27
 * @since 1.0.0
 */
public class BeanTypeUtil {

    /**
     * 获取简单字段的随机值
     * <p>
     * 简单字段含义:
     * <p>
     * String, 时间, 基本类型, 包装类型
     *
     * @param declaredField 声明字段
     *
     * @return
     */
    public static Object getSimpleFieldValue(Field declaredField) {

        Class<?> type = declaredField.getType();
        return getSimpleFieldValue(type);
    }

    public static <T> Object getSimpleFieldValue(Class<T> type) {
        // 如果是 基本类型
        if (type == int.class || type == Integer.class) {
            return RandomUtil.randomInt();
        } else if (type == double.class || type == Double.class) {
            return RandomUtil.randomDouble();
        } else if (type == long.class || type == Long.class) {
            return RandomUtil.randomLong();
        } else if (type == float.class || type == Float.class) {
            return RandomUtil.randomFloat();
        } else if (type == String.class) {
            return RandomUtil.randomString();
        } else if (type == byte.class || type == Byte.class) {
            return RandomUtil.randomByte();
        } else if (type == short.class || type == Short.class) {
            return RandomUtil.randomShort();
        } else if (type == boolean.class || type == Boolean.class) {
            return RandomUtil.randomBoolean();
        } else if (type == char.class || type == Character.class) {
            return RandomUtil.randomCharacter();
        } else if (type == BigDecimal.class) {
            return new BigDecimal(RandomUtil.randomInt());
        }else if (type == BigInteger.class) {
            return new BigDecimal(RandomUtil.randomInt());
        }
        // 时间
        else if (type == Date.class) {
            return RandomUtil.randomDate();
        } else if (type == LocalDate.class) {
            return RandomUtil.randomLocalDate();
        } else if (type == LocalDateTime.class) {
            return RandomUtil.randomLocalDateTime();
        } else {
            throw new IllegalArgumentException("字段不是简单类型");
        }
    }

    /**
     * 判断是否为简单字段
     *
     * @param tClass
     *
     * @return
     */
    public static <T> boolean isSimpleField(Class<T> tClass) {
        // 时间
        if (tClass == Date.class) {
            return true;
        } else if (tClass == LocalDate.class) {
            return true;
        } else if (tClass == LocalDateTime.class) {
            return true;
        } else if (tClass == String.class) {
            return true;
        } else if(tClass == BigDecimal.class){
            return true;
        }else if(tClass == BigInteger.class){
            return true;
        }

        // 如果是 基本类型
        try {
            return tClass.isPrimitive() || ((Class) tClass.getField("TYPE").get(null)).isPrimitive();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}

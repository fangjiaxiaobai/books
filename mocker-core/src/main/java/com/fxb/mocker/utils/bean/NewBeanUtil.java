package com.fxb.mocker.utils.bean;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.util.ReflectionUtils;

import com.fxb.mocker.utils.model.NewBeanModel;
import com.fxb.mocker.utils.model.NewFieldBeanModel;
import com.fxb.mocker.utils.model.NewGenericBeanModel;
import com.fxb.mocker.utils.model.NewParamterBeanModel;
import com.fxb.mocker.utils.random.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
     * @param <T> 类型
     *
     * @return 对象
     */
    public static <T> T newInstance(NewBeanModel newBeanModel) throws Exception {

        // 判断是否为数组
        Class<?> tClass = newBeanModel.getClazz();
        boolean isArray = tClass.isArray();
        if (isArray) {
            System.out.println("isArray");
            return doParseArray(newBeanModel);
        }
        // 判断是否为集合
        else if (Collection.class.isAssignableFrom(tClass)) {
            System.out.println("isCollection");
            return doParseCollection(newBeanModel);
        } else if (Map.class.isAssignableFrom(tClass)) {
            System.out.println("isCollection");
            return doParseMap(newBeanModel);
        }
        // 判断是否为接口
        else if (tClass.isInterface()) {
            System.out.println("isInterface");
            return doParseInterface(newBeanModel);
        }
        // 判断是否为简单类型:(基本类型,包装类型,时间类型,String)
        if (BeanTypeUtil.isSimpleField(tClass)) {
            return (T) BeanTypeUtil.getSimpleFieldValue(tClass);
        } else {
            return newObject(newBeanModel);
        }
    }

    public static <T> T newObject(NewBeanModel newBeanModel) throws Exception {
        // 如果是自定义类,则获取是否有父类
        Class<?> tClass = newBeanModel.getClazz();
        Field[] declaredFields = tClass.getDeclaredFields();

        // 如果类中含有 final 修饰的字段
        Constructor<?>[] declaredConstructors = tClass.getDeclaredConstructors();
        Constructor<T> declaredConstructor = (Constructor<T>) declaredConstructors[0];
        // 优先选择无参构造函数
        int minParemeterCount = Integer.MAX_VALUE;
        for (Constructor<?> constructor : declaredConstructors) {
            if (constructor.getParameterCount() == 0) {
                declaredConstructor = (Constructor<T>) constructor;
                break;
            }
            if (constructor.getParameterCount() < minParemeterCount) {
                minParemeterCount = constructor.getParameterCount();
                declaredConstructor = (Constructor<T>) constructor;
            }
        }

        ReflectionUtils.makeAccessible(declaredConstructor);
        System.out.println(declaredConstructor.getDeclaringClass());
        Object[] parameterObjects = getParameterObjects(declaredConstructor, newBeanModel.getMethod(),
                                                        newBeanModel.getType());
        Object t = declaredConstructor.newInstance(parameterObjects);
        // 获取类的字段
        for (Field declaredField : declaredFields) {
            int modifiers = declaredField.getModifiers();
            // 过滤掉 static final 修复的字段
            if (0x0010 == (0x0010 & modifiers)) {
                System.out.println(modifiers + "----continues----" + declaredField.getName());
                continue;
            }
            // 专为高途
            if (declaredField.getName().equals("code") || declaredField.getName().equals("status")) {
                ReflectionUtils.makeAccessible(declaredField);
                ReflectionUtils.setField(declaredField, t, 0);
                continue;
            }
            // 判断字段的类型。基本类型,包装类型,String,Date,自定义类型,
            if (declaredField.getGenericType() instanceof TypeVariable) {
                Type genericReturnType = newBeanModel.getMethod().getGenericReturnType();
                ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
                Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
                Object o = doParseGeneric(newBeanModel.getMethod(), actualTypeArgument);
                ReflectionUtils.makeAccessible(declaredField);
                ReflectionUtils.setField(declaredField, t, o);
                continue;
            }
            NewFieldBeanModel newFieldBeanModel = new NewFieldBeanModel(newBeanModel.getMethod(), TypeEnum.Field,
                                                                        declaredField, declaredField.getType());
            Object instance = newInstance(newFieldBeanModel);
            ReflectionUtils.makeAccessible(declaredField);
            ReflectionUtils.setField(declaredField, t, instance);
        }
        return (T) t;
    }

    /**
     * 解析构造方法, 返回构造方式的参数
     *
     * @param constructor 构造方法
     * @param method
     *
     * @return
     * @throws Exception
     */
    private static <T> Object[] getParameterObjects(Constructor<T> constructor, Method method,
            TypeEnum typeEnum) throws Exception {
        int parameterCount = constructor.getParameterCount();
        Parameter[] parameters = constructor.getParameters();
        Object[] objects = new Object[parameterCount];

        Type genericReturnType = method.getGenericReturnType();
        Type[] actualTypeArguments = new Type[]{};
        if(genericReturnType instanceof ParameterizedType){
            actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        }

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            // 如果 构造函数的参数，是简单类型
            if (BeanTypeUtil.isSimpleField(parameter.getType())) {
                // 专为 gaotu 设置，如果字段是code或者status，设置成0 ,后面会改成配置
                if (parameter.getType() == int.class || parameter.getType() == Integer.TYPE) {
                    objects[i] = 0;
                    continue;
                }

                objects[i] = BeanTypeUtil.getSimpleFieldValue(parameter.getType());
            }
            // 如果不是简单类型
            else {
                Class<?> aClass = null;
                if (TypeEnum.Method == typeEnum) {
                    if (parameter.getParameterizedType() instanceof TypeVariable) {
                        Object instance = doParseGeneric(method, actualTypeArguments[0]);
                        objects[i] = instance;
                        continue;
                    } else {
                        aClass = parameter.getType();
                    }
                } else if (TypeEnum.Paramter == typeEnum) {
                    Type parameterizedType = parameter.getParameterizedType();
                    String parameterizedTypeName = parameterizedType.getTypeName();
                    aClass = Class.forName(parameterizedTypeName);
                }
                NewParamterBeanModel newParamterBeanModel = new NewParamterBeanModel(method, TypeEnum.Paramter, null,
                                                                                     aClass);
                Object instance = newInstance(newParamterBeanModel);
                objects[i] = instance;
            }
        }
        return objects;
    }

    /**
     * 解析泛型类型
     * <p>
     * 可能是嵌套类型
     *
     * @param type 泛型类型
     *
     * @return
     */
    private static Object doParseGeneric(Method method, Type type) throws Exception {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length != 1) {
                throw new RuntimeException("不支持多泛型");
            }
            Type actualTypeArgument = actualTypeArguments[0];
            String typeName1 = actualTypeArgument.getTypeName();
            String typeName = rawType.getTypeName();
            Class<?> aClass = Class.forName(typeName);
            Class<?> aClass1 = Class.forName(typeName1);
            Object instance = newInstance(new NewGenericBeanModel(method, TypeEnum.Generic, aClass1, aClass));
            return instance;
        } else if(type instanceof Class){
            String typeName = type.getTypeName();
            Class<?> aClass = Class.forName(typeName);
            return newInstance(new NewGenericBeanModel(method, TypeEnum.Generic, null, aClass));
        }
        return null;
    }

    /**
     * 解析接口
     *
     * @param <T>    指定类型T
     * @param tClass 目标类
     *
     * @return t
     */
    private static <T> T doParseInterface(NewBeanModel tClass) {
        return null;
    }

    /**
     * 解析Map
     *
     * @param <T>    指定类型T
     * @param tClass 目标类
     *
     * @return T
     */
    private static <T> T doParseMap(NewBeanModel tClass) {
        log.error("不支持Map,暂且给您返回null了,建议使用对象代替Map,然而，我们后续会支持的");
        return null;
    }

    /**
     * 解析集合
     *
     * @param <T> 指定类型T
     *
     * @return T
     */
    private static <T> T doParseCollection(NewBeanModel newBeanModel) throws Exception {
        Class<?> clazz = newBeanModel.getClazz();
        String canonicalName = clazz.getCanonicalName();
        Class<?> aClass = null;
        if (TypeEnum.Method == newBeanModel.getType()) {
            Type genericType = newBeanModel.getMethod().getGenericReturnType();
            Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
            if (actualTypeArguments.length == 0) {
                log.error("未指定泛型类型,oh，god, 请设置java的版本为1.4, 然后我会告诉你,不支持java版本1.4，请使用1.8！下一个！！！");
            }
            String genericClassName = (actualTypeArguments[0]).getTypeName();
            aClass = Class.forName(genericClassName);
        } else if (TypeEnum.Field == newBeanModel.getType()) {
            NewFieldBeanModel newFieldBeanModel = (NewFieldBeanModel) newBeanModel;
            // 字段是list
            Type genericType = newFieldBeanModel.getField().getGenericType();

            Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
            if (actualTypeArguments.length == 0) {
                log.error("未指定泛型类型,oh，god, 请设置java的版本为1.4, 然后我会告诉你,不支持java版本1.4，请使用1.8！下一个！！！");
            }
            String genericClassName = (actualTypeArguments[0]).getTypeName();
            aClass = Class.forName(genericClassName);
        } else if (newBeanModel instanceof NewGenericBeanModel) {
            NewGenericBeanModel newGenericBeanModel = (NewGenericBeanModel) newBeanModel;
            aClass = newGenericBeanModel.getGenericClazz();
        }

        Collection<Object> collection = new ArrayList<>();
        int i = RandomUtil.randomIntInThree();
        System.out.println("随机生成数:" + i);
        for (int j = 0; j < i; j++) {
            NewGenericBeanModel newGenericBeanModel = new NewGenericBeanModel(newBeanModel.getMethod(),
                                                                              TypeEnum.Generic, null, aClass);
            collection.add(newInstance(newGenericBeanModel));
        }
        Collection<Object> objects = null;
        if ("java.util.List".equals(canonicalName) || "java.util.ArrayList".equals(canonicalName)) {
            objects = Lists.newArrayList(collection);
        } else if ("java.util.LinkedList".equals(canonicalName)) {
            objects = Lists.newLinkedList();
            objects.addAll(collection);
        } else if ("java.util.TreeSet".equals(canonicalName)) {
            objects = new TreeSet<>(collection);
        } else if ("java.util.Set".equals(canonicalName) || "java.util.HashSet".equals(canonicalName)) {
            objects = Sets.newHashSet(collection);
        } else {
            log.error("暂不支持出 ArrayList, LinkedList之外的类型");
        }
        return (T) objects;
    }

    /**
     * 解析数组类型
     *
     * @param <T> 指定类型T
     *
     * @return T
     */
    private static <T> T doParseArray(NewBeanModel newBeanModel) throws Exception {
        Class<?> clazz = newBeanModel.getClazz();
        String canonicalName = clazz.getCanonicalName();
        if (canonicalName.endsWith("[][]")) {
            throw new RuntimeException("目前不支持多维数据");
        }

        String className = canonicalName.replace("[]", "");
        Class<?> aClass = Class.forName(className);
        int length = RandomUtil.randomIntInThree();
        Object array = Array.newInstance(aClass, length);
        for (int i = 0; i < length; i++) {
            Array.set(array, i, newInstance(newBeanModel));
        }
        return (T) array;
    }

}

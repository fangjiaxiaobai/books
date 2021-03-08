package feign.handler;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxb.mocker.annotation.Mocker;
import com.fxb.mocker.utils.bean.NewBeanUtil;

import feign.InvocationHandlerFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * mocker处理器的选择器
 *
 * @author fangjiaxiaobai
 * @date 2021-02-20 19:31
 * @since 1.0.0
 */
@Slf4j
public class MockerHandlerSelector {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Object select(Method method) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        Mocker mocker = method.getAnnotation(Mocker.class);
        if (mocker != null) {
            if (mocker.autoGen()) {
                // 自动生成, 实现解析返回参数的方法
                Class<?> returnType = method.getReturnType();
                log.error("<Mocker> 执行Mock方法,生成 {} 对象,并为其自动填充数据", returnType.getCanonicalName());
                return NewBeanUtil.newInstance(returnType);
            } else if (!mocker.autoGen() || StringUtils.isNotEmpty(mocker.path())) {
                // 读取resources目录下的文件,获取 方法对应的返回数据
                if (StringUtils.isNotEmpty(mocker.path())) {
                    // 1、按照Mocker注解中指定的路径查找
                    return parseMethodReturnObject(mocker.path(), method.getName(), method.getReturnType());
                } else {
                    // 2、按照方法名匹配 文件名  比如:com.fxb.mocker.TestController.json
                    // com.fxb.mocker.TestController是类名，
                    // 文件格式为{key:{value},key2:{value2}}, key为方法名,{value},{value2}为返回值的类型
                    Class<?> declaringClass = method.getDeclaringClass();
                    String clazzName = declaringClass.getCanonicalName() + ".json";
                    return parseMethodReturnObject(clazzName, method.getName(), method.getReturnType());
                }
            }
        }

        throw new RuntimeException("未找到mocker注解");
    }

    /**
     * 解析出 方法返回的对象
     *
     * @param <T>
     *
     * @return
     */
    private static <T> T parseMethodReturnObject(String filePath, String methodName,
            Class<T> tClass) throws IOException {
        JsonNode jsonNode = getJsonNode(filePath);
        JsonNode path = jsonNode.path(methodName);
        Object o = OBJECT_MAPPER.readValue(path.toString(), tClass);
        return (T) o;
    }

    private static JsonNode getJsonNode(String filePath) throws IOException {
        InputStream resourceAsStream = MockerHandlerSelector.class.getClassLoader().getResourceAsStream(filePath);
        byte[] bytes = IOUtils.readFully(resourceAsStream, resourceAsStream.available());
        return OBJECT_MAPPER.readTree(bytes);
    }

}

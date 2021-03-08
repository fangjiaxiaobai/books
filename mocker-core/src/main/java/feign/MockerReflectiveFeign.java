package feign;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fxb.mocker.annotation.Mocker;

import feign.handler.MockerHandlerSelector;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fangjiaxiaobai
 * @date 2021-02-20 10:22
 * @since 1.0.0
 */
@Slf4j
public class MockerReflectiveFeign extends ReflectiveFeign {

    private final ParseHandlersByName targetToHandlersByName;

    private final InvocationHandlerFactory factory;

    MockerReflectiveFeign(ParseHandlersByName targetToHandlersByName, InvocationHandlerFactory factory) {
        super(targetToHandlersByName, factory);
        this.targetToHandlersByName = targetToHandlersByName;
        this.factory = factory;
    }

    @Override
    public <T> T newInstance(Target<T> target) {
        Map<String, InvocationHandlerFactory.MethodHandler> nameToHandler = targetToHandlersByName.apply(target);
        Map<Method, InvocationHandlerFactory.MethodHandler> methodToHandler = new LinkedHashMap<Method, InvocationHandlerFactory.MethodHandler>();
        List<DefaultMethodHandler> defaultMethodHandlers = new LinkedList<DefaultMethodHandler>();

        for (Method method : target.type().getMethods()) {
            Mocker annotation = method.getAnnotation(Mocker.class);
            if (method.getDeclaringClass() == Object.class) {
                continue;
            } else if (Util.isDefault(method)) {
                DefaultMethodHandler handler = new DefaultMethodHandler(method);
                defaultMethodHandlers.add(handler);
                methodToHandler.put(method, handler);
            } else {
                if (annotation != null) {
                    InvocationHandlerFactory.MethodHandler methodHandler = argv -> {
                        // 实现解析返回参数的方法
                        Class<?> returnType = method.getReturnType();
                        log.error("<Mocker> 执行Mock方法,生成 {} 对象,并为其自动填充数据", returnType.getCanonicalName());
                        return MockerHandlerSelector.select(method);
                    };
                    methodToHandler.put(method, methodHandler);
                } else {
                    methodToHandler.put(method, nameToHandler.get(Feign.configKey(target.type(), method)));
                }
            }

        }
        InvocationHandler handler = factory.create(target, methodToHandler);
        T proxy = (T) Proxy.newProxyInstance(target.type().getClassLoader(), new Class<?>[] { target.type() }, handler);

        for (DefaultMethodHandler defaultMethodHandler : defaultMethodHandlers) {
            defaultMethodHandler.bindTo(proxy);
        }
        return proxy;
    }

    public static Feign.Builder builder() {
        return new MockerReflectiveFeign.Builder();
    }

    public static class Builder extends Feign.Builder {

        public Builder() {
            super();
        }

        @SneakyThrows
        @Override
        public Feign build() {
            Feign build = super.build();
            if (build instanceof ReflectiveFeign) {
                ReflectiveFeign reflectiveFeign = (ReflectiveFeign) build;

                Class<? extends ReflectiveFeign> aClass = reflectiveFeign.getClass();
                Field targetToHandlersByName = aClass.getDeclaredField("targetToHandlersByName");
                targetToHandlersByName.setAccessible(true);
                ParseHandlersByName parseHandlersByName = (ParseHandlersByName) targetToHandlersByName.get(
                        reflectiveFeign);
                Field factory = aClass.getDeclaredField("factory");
                factory.setAccessible(true);
                InvocationHandlerFactory invocationHandlerFactory = (InvocationHandlerFactory) factory.get(
                        reflectiveFeign);
                return new MockerReflectiveFeign(parseHandlersByName, invocationHandlerFactory);
            }
            return build;
        }
    }
}

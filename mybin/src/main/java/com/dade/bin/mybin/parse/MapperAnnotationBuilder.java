package com.dade.bin.mybin.parse;

import com.dade.bin.mybin.annotations.*;
import com.dade.bin.mybin.session.BINConfig;
import com.dade.bin.mybin.session.FieldEntity;
import com.dade.bin.mybin.session.ReturnEntity;
import com.google.common.collect.Maps;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

public class MapperAnnotationBuilder {

    private final List<BINConfig> configurations;
    private final Class<?> type;

    public MapperAnnotationBuilder(List<BINConfig> configurations, Class<?> type) {
        this.configurations = configurations;
        this.type = type;
    }

    /**
     * 类级别注解解析
     * TODO 将xml改为java配置或许可以用到这里
     */
    private void parseClassAnnotation() {
    }

    /**
     * 解析方法级别的注解
     */
    private void parseMethodAnnotation() {
        Method[] methods = type.getMethods();
        for (Method m : methods) {
            BINConfig configuration = new BINConfig();
            configuration.setMethod(m);
            Annotation[] annotations = m.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Block) {
                    int len = ((Block) annotation).len();
                    boolean reverse = ((Block) annotation).reverse();
                    notNull(len, "Property 'len' is required");
                    notNull(reverse, "Property 'reverse' is required");
                    configuration.setBlockLen(len);
                    configuration.setReverse(reverse);
                }
                if (annotation instanceof SubBlock) {
                    int len = ((SubBlock) annotation).len();
                    notNull(len, "Property 'len' is required");
                    configuration.setSubBlockLen(len);
                }
                if (annotation instanceof FileName) {
                    String fileName = ((FileName) annotation).value();
//                    notNull(fileName, "Property 'fileName' is required");
                    configuration.setFileName(fileName);
                }
                if (annotation instanceof FilePackage) {
                    String url = ((FilePackage) annotation).value();
                    notNull(url, "Property 'url' is required");
                    configuration.setFilePackage(url);
                    configuration.setFilePackageRegex(((FilePackage) annotation).regex());
                }
                if (annotation instanceof RegularConfigs) {
                    RegularConfig[] configs = ((RegularConfigs) annotation).value();
                    // TODO 是否需要处理多线程
                    Map<Integer, Integer> conMap = Maps.newConcurrentMap();
                    for (RegularConfig config : configs) {
                        conMap.put(config.order(), config.len());
                    }
                    configuration.setRegularConfig(conMap);
                }
            }
            parseResultMap(m, configuration);
            this.configurations.add(configuration);
        }

    }

    private void parseResultMap(Method method, BINConfig configuration) {
        ReturnEntity re = new ReturnEntity();
        // TODO 多线程思考
        Map<Integer, FieldEntity> map = Maps.newHashMap();
        Class<?> returnType = method.getReturnType();
        if (Collection.class.isAssignableFrom(returnType)){
            re.setReturnType(returnType);
            Type reType = method.getGenericReturnType();
            re.setCollection(true);
            if (reType instanceof ParameterizedType){
                Type[] actualTypeArguments = ((ParameterizedType) reType).getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    reType = actualTypeArguments[0];
                    if (reType instanceof Class) {
                        returnType = (Class<?>) reType;
                        Field[] fields = returnType.getDeclaredFields();
                        for (Field field : fields) {
                            Order order = field.getAnnotation(Order.class);
                            String name = field.getName();
                            Class<?> type = field.getType();
                            map.put(order.value(), new FieldEntity(name, type));
                        }
                        re.setRealType(returnType);
                        re.setFieldMap(map);
                        configuration.setReturnEntity(re);
                    } else if (reType instanceof ParameterizedType) {
                        returnType = (Class<?>) ((ParameterizedType) reType).getRawType();
                    }
                }
            }
        } else if (Map.class.isAssignableFrom(returnType)){
            // TODO 添加对Map的支持
        } else{
            Field[] fields = returnType.getDeclaredFields();
            for (Field field : fields) {
                Order order = field.getAnnotation(Order.class);
                String name = field.getName();
                Class<?> type = field.getType();
                map.put(order.value(), new FieldEntity(name, type));
            }
            re.setReturnType(returnType);
            re.setFieldMap(map);
            configuration.setReturnEntity(re);
        }
    }

    public void parse() {
        parseClassAnnotation();
        parseMethodAnnotation();
    }

}

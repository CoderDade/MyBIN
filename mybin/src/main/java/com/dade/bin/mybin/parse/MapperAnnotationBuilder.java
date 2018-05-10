package com.dade.bin.mybin.parse;

import com.dade.bin.mybin.annotations.*;
import com.dade.bin.mybin.session.BINConfig;
import com.dade.bin.mybin.session.ReturnEntity;
import com.google.common.collect.Maps;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class MapperAnnotationBuilder {

    private final BINConfig configuration;
    private final Class<?> type;

    public MapperAnnotationBuilder(BINConfig configuration, Class<?> type) {
        this.configuration = configuration;
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
            Annotation[] annotations = m.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Block) {
                    configuration.setBlockLen(((Block) annotation).len());
                    configuration.setReverse(((Block) annotation).reverse());
                }
                if (annotation instanceof SubBlock) {
                    configuration.setSubBlockLen(((SubBlock) annotation).len());
                }
                if (annotation instanceof FileName) {
                    configuration.setFileName(((FileName) annotation).value());
                }
                if (annotation instanceof FilePackage) {
                    configuration.setFilePackage(((FilePackage) annotation).value());
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
            parseResultMap(m);
        }

    }

    private void parseResultMap(Method method) {
        ReturnEntity re = new ReturnEntity();
        // TODO 多线程思考
        Map<String, Integer> map = Maps.newHashMap();
        Class<?> returnType = method.getReturnType();
        Field[] fields = returnType.getDeclaredFields();
        for (Field field : fields) {
            Order order = field.getAnnotation(Order.class);
            String name = field.getName();
            map.put(name, order.value());
        }
        re.setReturnType(returnType);
        re.setFieldMap(map);
        configuration.setReturnEntity(re);
    }

    public void parse() {
        parseClassAnnotation();
        parseMethodAnnotation();
    }

}

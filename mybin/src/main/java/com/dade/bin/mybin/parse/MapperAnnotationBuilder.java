package com.dade.bin.mybin.parse;

import com.dade.bin.mybin.annotations.*;
import com.dade.bin.mybin.session.BINConfig;
import com.dade.bin.mybin.session.ReturnEntity;
import com.google.common.collect.Maps;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

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

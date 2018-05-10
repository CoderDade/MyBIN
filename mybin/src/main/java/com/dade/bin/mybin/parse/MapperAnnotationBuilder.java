package com.dade.bin.mybin.parse;

import com.dade.bin.mybin.annotations.*;
import com.dade.bin.mybin.reflection.TypeParameterResolver;
import com.dade.bin.mybin.session.BINConfig;
import com.google.common.collect.Maps;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

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
    private void parseClassAnnotation(){}

    /**
     * 解析方法级别的注解
     */
    private void parseMethodAnnotation(){
        Method[] methods = type.getMethods();
        for(Method m : methods){
            Annotation[] annotations = m.getAnnotations();
            for(Annotation annotation : annotations){
                if(annotation instanceof Block){
                    configuration.setBlockLen(((Block)annotation).len());
                    configuration.setReverse(((Block)annotation).reverse());
                }
                if(annotation instanceof SubBlock){
                    configuration.setSubBlockLen(((SubBlock)annotation).len());
                }
                if(annotation instanceof FileName){
                    configuration.setFileName(((FileName)annotation).value());
                }
                if(annotation instanceof FilePackage){
                    configuration.setFilePackage(((FilePackage)annotation).value());
                    configuration.setFilePackageRegex(((FilePackage)annotation).regex());
                }
                if (annotation instanceof RegularConfigs){
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
        Class<?> returnType = getReturnType(method);



    }

    private Class<?> getReturnType(Method method) {
        Class<?> returnType = method.getReturnType();
        Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, type);
        if (resolvedReturnType instanceof Class) {
            returnType = (Class<?>) resolvedReturnType;
            if (returnType.isArray()) {
                returnType = returnType.getComponentType();
            }
        } else if (resolvedReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) resolvedReturnType;
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();
            if (Collection.class.isAssignableFrom(rawType)) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    Type returnTypeParameter = actualTypeArguments[0];
                    if (returnTypeParameter instanceof Class<?>) {
                        returnType = (Class<?>) returnTypeParameter;
                    } else if (returnTypeParameter instanceof ParameterizedType) {
                        // (gcode issue #443) actual type can be a also a parameterized type
                        returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
                    } else if (returnTypeParameter instanceof GenericArrayType) {
                        Class<?> componentType = (Class<?>) ((GenericArrayType) returnTypeParameter).getGenericComponentType();
                        // (gcode issue #525) support List<byte[]>
                        returnType = Array.newInstance(componentType, 0).getClass();
                    }
                }
            }
        }

        return returnType;
    }

    public void parse() {
        parseClassAnnotation();
        parseMethodAnnotation();
    }

}

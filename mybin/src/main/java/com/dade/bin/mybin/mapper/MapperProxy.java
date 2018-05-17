package com.dade.bin.mybin.mapper;

import com.dade.bin.mybin.binding.MapperMethod;
import com.dade.bin.mybin.session.DefaultBinSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class MapperProxy<T> implements InvocationHandler, Serializable {

    private final DefaultBinSession defaultBinSession;
    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxy(DefaultBinSession defaultBinSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.defaultBinSession = defaultBinSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return defaultBinSession.clean(method);
    }

//    private MapperMethod cachedMapperMethod(Method method) {
//        MapperMethod mapperMethod = methodCache.get(method);
//        if (mapperMethod == null) {
//            mapperMethod = new MapperMethod(mapperInterface, method, defaultBinSession.getConfiguration());
//            methodCache.put(method, mapperMethod);
//        }
//        return mapperMethod;
//    }

}

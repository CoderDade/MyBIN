package com.dade.bin.mybin.binding;

import com.dade.bin.mybin.mapper.MapperProxy;
import com.dade.bin.mybin.session.BINSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<Method, MapperMethod>();

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    protected T newInstance(MapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);
    }

    public T newInstance(BINSession binSession) {
        final MapperProxy<T> mapperProxy = new MapperProxy<T>(binSession, mapperInterface, methodCache);
        return newInstance(mapperProxy);
    }

}

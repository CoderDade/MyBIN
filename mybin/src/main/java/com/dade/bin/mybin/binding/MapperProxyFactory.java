package com.dade.bin.mybin.binding;

import com.dade.bin.mybin.mapper.MapperProxy;
import com.dade.bin.mybin.session.DefaultBinSession;

import java.lang.reflect.Proxy;

public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    protected T newInstance(MapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);
    }

    public T newInstance(DefaultBinSession defaultBinSession) {
        final MapperProxy<T> mapperProxy = new MapperProxy<T>(defaultBinSession);
        return newInstance(mapperProxy);
    }

}

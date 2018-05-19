package com.dade.bin.mybin.mapper;

import com.dade.bin.mybin.session.DefaultBinSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy<T> implements InvocationHandler, Serializable {

    private final DefaultBinSession defaultBinSession;

    public MapperProxy(DefaultBinSession defaultBinSession) {
        this.defaultBinSession = defaultBinSession;
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

}

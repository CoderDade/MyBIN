package com.dade.bin.mybin.mapper;

import com.dade.bin.mybin.session.BINSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy implements InvocationHandler {

    BINSession binSession;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return binSession.clean();
    }
}

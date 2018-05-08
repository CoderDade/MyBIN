package com.dade.bin.mybin.binding;

import com.dade.bin.mybin.session.BINConfig;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {

    private final BINConfig config;

    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<Class<?>, MapperProxyFactory<?>>();

    public MapperRegistry(BINConfig config) {
        this.config = config;
    }
}

package com.dade.bin.mybin.session;

import com.dade.bin.mybin.binding.MapperRegistry;
import com.dade.bin.mybin.executor.Executor;

import java.lang.reflect.Method;
import java.util.List;

import static org.springframework.util.Assert.notNull;

public class DefaultBinSession {

    private final List<BinConfig> binConfigs;
    private final Executor executor;
    private final MapperRegistry mapperRegistry;

    public DefaultBinSession(List<BinConfig> binConfigs, Executor executor) {
        this.binConfigs = binConfigs;
        this.executor = executor;
        this.mapperRegistry = new MapperRegistry(this.binConfigs);
    }

    public Object clean(Method method) {
        BinConfig config =
                binConfigs.stream().filter(con -> con.getMethod().getName().equals(method.getName())).findFirst().orElse(null);
        notNull(config, "Property 'config' is required");
        return executor.clean(config);
    }

    public List<BinConfig> getConfiguration() {
        return binConfigs;
    }

    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type, this);
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

}

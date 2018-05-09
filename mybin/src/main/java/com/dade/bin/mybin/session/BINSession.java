package com.dade.bin.mybin.session;

import com.dade.bin.mybin.executor.Executor;

public class BINSession {

    private final BINConfig binConfig;
    private final Executor executor;

    public BINSession(BINConfig binConfig, Executor executor){
        this.binConfig = binConfig;
        this.executor = executor;
    }

    public Object clean(){
        return executor.clean(binConfig);
    }

    public BINConfig getConfiguration() {
        return binConfig;
    }

    public <T> T getMapper(Class<T> type) {
        return binConfig.<T>getMapper(type, this);
    }


}

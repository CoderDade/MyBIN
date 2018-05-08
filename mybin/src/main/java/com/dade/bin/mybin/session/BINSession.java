package com.dade.bin.mybin.session;

import com.dade.bin.mybin.executor.Executor;

public class BINSession {

    BINConfig binConfig;
    Executor executor;

    public BINSession(BINConfig binConfig, Executor executor){
        this.binConfig = binConfig;
        this.executor = executor;
    }

    public Object clean(){
        return executor.clean(binConfig);
    }

}

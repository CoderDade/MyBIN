package com.dade.bin.mybin.executor.resultset;

import com.google.common.collect.Maps;

import java.util.Map;

public class ResultSet {

    private final Map<Integer, byte[]> resultMap;

    public ResultSet(Map<Integer, byte[]> resultMap) {
        this.resultMap = resultMap;
    }

}

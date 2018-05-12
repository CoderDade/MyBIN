package com.dade.bin.mybin.executor.resultset;

import java.util.List;
import java.util.Map;

public class ResultSet {

    private final List<Map<Integer, byte[]>> resultMaps;

    public ResultSet(List<Map<Integer, byte[]>> resultMaps) {
        this.resultMaps = resultMaps;
    }

    public List<Map<Integer, byte[]>> getResultMaps() {
        return resultMaps;
    }

}

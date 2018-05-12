package com.dade.bin.mybin.executor.resultset;

import com.dade.bin.mybin.util.HexUtil;
import com.google.common.collect.Maps;

import java.util.Map;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.Assert.state;

public class ResultSet {

    private final Map<Integer, byte[]> resultMap;

    public ResultSet(Map<Integer, byte[]> resultMap) {
        this.resultMap = resultMap;
    }

    public Map<Integer, byte[]> getResultMap() {
        return resultMap;
    }

    public String getStringValue(Integer len){
        state(!isLenLegal(len), "len must be legal!");
        byte[] bytes = resultMap.get(len);
        return HexUtil.getStringFromBytes(bytes);
    }

    public Integer getIntegerValue(Integer len){
        state(!isLenLegal(len), "len must be legal!");
        byte[] bytes = resultMap.get(len);
        return HexUtil.bytesToInteger(bytes, 10);
    }

    private boolean isLenLegal(Integer len){
        notNull(len, "Property 'len' is required");
        if (len <0 || len>=this.resultMap.size()){
            return false;
        }
        return true;
    }

}

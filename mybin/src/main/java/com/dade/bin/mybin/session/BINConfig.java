package com.dade.bin.mybin.session;

import com.dade.bin.mybin.binding.MapperRegistry;

import java.util.Map;

public class BINConfig {

    Integer blockLen;
    Boolean isReverse;

    Integer subBlockLen;
    String fileName;
    String filePackage;

    Map<String, Integer> regularConfig;

    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);

    public <T> T getMapper(Class<T> type, BINSession session) {
        return mapperRegistry.getMapper(type, session);
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public Integer getBlockLen() {
        return blockLen;
    }

    public void setBlockLen(Integer blockLen) {
        this.blockLen = blockLen;
    }

    public Boolean getReverse() {
        return isReverse;
    }

    public void setReverse(Boolean reverse) {
        isReverse = reverse;
    }

    public Integer getSubBlockLen() {
        return subBlockLen;
    }

    public void setSubBlockLen(Integer subBlockLen) {
        this.subBlockLen = subBlockLen;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePackage() {
        return filePackage;
    }

    public void setFilePackage(String filePackage) {
        this.filePackage = filePackage;
    }

    public Map<String, Integer> getRegularConfig() {
        return regularConfig;
    }

    public void setRegularConfig(Map<String, Integer> regularConfig) {
        this.regularConfig = regularConfig;
    }
}

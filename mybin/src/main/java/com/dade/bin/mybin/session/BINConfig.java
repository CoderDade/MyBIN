package com.dade.bin.mybin.session;

import com.dade.bin.mybin.binding.MapperRegistry;

import java.lang.reflect.Method;
import java.util.Map;

public class BINConfig {

    Integer blockLen;
    Boolean isReverse;

    Integer subBlockLen;
    String fileName;
    String filePackage;
    String filePackageRegex;
    Method method;

    Map<Integer, Integer> regularConfig;

    ReturnEntity returnEntity;

    public ReturnEntity getReturnEntity() {
        return returnEntity;
    }

    public void setReturnEntity(ReturnEntity returnEntity) {
        this.returnEntity = returnEntity;
    }

    public String getFilePackageRegex() {
        return filePackageRegex;
    }

    public void setFilePackageRegex(String filePackageRegex) {
        this.filePackageRegex = filePackageRegex;
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

    public Map<Integer, Integer> getRegularConfig() {
        return regularConfig;
    }

    public void setRegularConfig(Map<Integer, Integer> regularConfig) {
        this.regularConfig = regularConfig;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}

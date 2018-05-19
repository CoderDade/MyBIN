package com.dade.bin.mybin.session;

import java.lang.reflect.Method;
import java.util.Map;

public class BinConfig {

    /**
     * 一块数据的长度的字节数
     */
    Integer blockLen;
    /**
     * 整形数据是否要翻转再转换
     */
    Boolean isReverse;
    /**
     *  块数据内子元素长度的字节数
     */
    Integer subBlockLen;
    /**
     *  要清洗的文件名
     */
    String fileName;
    /**
     *  要清洗的文件路径（包括文件名）
     */
    String filePackage;
    /**
     *  要清洗的文件名的正则
     */
    String filePackageRegex;
    /**
     *  接口下的具体方法
     */
    Method method;
    /**
     *  特殊配置
     */
    Map<Integer, Integer> regularConfig;
    /**
     *  返回值
     */
    ResultEntity resultEntity;

    public ResultEntity getResultEntity() {
        return resultEntity;
    }

    public void setResultEntity(ResultEntity resultEntity) {
        this.resultEntity = resultEntity;
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

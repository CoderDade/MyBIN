package com.dade.bin.mybin.session;

import java.util.Map;

public class ResultEntity {

    /**
     * 返回值类型
     */
    Class<?> resultType;
    /**
     * 字段顺序 和 详细信息
     */
    Map<Integer, FieldEntity> fieldMap;
    /**
     * 判断返回值是不是集合
     */
    boolean isCollection = false;
    /**
     * 如果返回值是集合，这个才是真正的返回值类型，如果不是集合，这个字段没有意义
     */
    Class<?> realType;


    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public Class<?> getRealType() {
        return realType;
    }

    public void setRealType(Class<?> realType) {
        this.realType = realType;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public Map<Integer, FieldEntity> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<Integer, FieldEntity> fieldMap) {
        this.fieldMap = fieldMap;
    }
}

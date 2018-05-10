package com.dade.bin.mybin.session;

import java.util.Map;

public class ReturnEntity {

    Class<?> returnType;
    Map<String, Integer> fieldMap;

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public Map<String, Integer> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Integer> fieldMap) {
        this.fieldMap = fieldMap;
    }
}

package com.dade.bin.mybin.session;

import java.util.Map;

public class ReturnEntity {

    Class<?> returnType;

    // 字段顺序 和 详细信息
    Map<Integer, FieldEntity> fieldMap;

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public Map<Integer, FieldEntity> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<Integer, FieldEntity> fieldMap) {
        this.fieldMap = fieldMap;
    }
}

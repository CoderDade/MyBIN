package com.dade.bin.mybin.session;

public class FieldEntity {

    String fieldName;
    Class<?> fieldTpye;

    public FieldEntity(String fieldName, Class<?> fieldTpye) {
        this.fieldName = fieldName;
        this.fieldTpye = fieldTpye;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class<?> getFieldTpye() {
        return fieldTpye;
    }

    public void setFieldTpye(Class<?> fieldTpye) {
        this.fieldTpye = fieldTpye;
    }
}

package com.dade.bin.test;

import com.dade.bin.mybin.annotations.Order;

public class BINTestEntity {

    @Order(1)
    Integer len;

    @Order(2)
    String str;

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "BINTestEntity{" +
                "len=" + len +
                ", str='" + str + '\'' +
                '}';
    }
}

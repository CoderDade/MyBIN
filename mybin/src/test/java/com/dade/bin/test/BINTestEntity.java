package com.dade.bin.test;

import com.dade.bin.mybin.annotations.Order;

public class BINTestEntity {

    @Order(1)
    Integer len;

    @Order(2)
    String str;

    @Override
    public String toString() {
        return "BINTestEntity{" +
                "len=" + len +
                ", str='" + str + '\'' +
                '}';
    }
}

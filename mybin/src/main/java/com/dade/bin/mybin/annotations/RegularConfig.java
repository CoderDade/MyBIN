package com.dade.bin.mybin.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Repeatable(RegularConfigs.class)
public @interface RegularConfig {
    int order();
    int len();
}

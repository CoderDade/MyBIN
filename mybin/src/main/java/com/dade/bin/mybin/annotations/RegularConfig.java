package com.dade.bin.mybin.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Repeatable(RegularConfigs.class)
public @interface RegularConfig {
    int order();
    int len();
}

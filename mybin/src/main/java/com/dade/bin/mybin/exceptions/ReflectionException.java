package com.dade.bin.mybin.exceptions;

public class ReflectionException extends BINException {

    private static final long serialVersionUID = 7642570221267566591L;

    public ReflectionException() {
        super();
    }

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(Throwable cause) {
        super(cause);
    }

}

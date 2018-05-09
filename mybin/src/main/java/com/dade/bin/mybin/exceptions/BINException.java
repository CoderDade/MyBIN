package com.dade.bin.mybin.exceptions;

public class BINException extends RuntimeException {

    private static final long serialVersionUID = 3880206998166270511L;

    public BINException() {
        super();
    }

    public BINException(String message) {
        super(message);
    }

    public BINException(String message, Throwable cause) {
        super(message, cause);
    }

    public BINException(Throwable cause) {
        super(cause);
    }

}

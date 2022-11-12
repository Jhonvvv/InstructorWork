package com.clm.common.base.exception;

/**
 * @Author su
 * @Date 2021/11/24 9:32
 */
public class ClmException extends RuntimeException{
    public ClmException(String message) {
        super(message);
    }

    public ClmException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClmException(Throwable cause) {
        super(cause);
    }
}

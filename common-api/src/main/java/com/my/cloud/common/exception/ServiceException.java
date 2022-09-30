package com.my.cloud.common.exception;

/**
 * 服务异常
 */
public class ServiceException extends Exception {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableSackTrace) {
        super(message, cause, enableSuppression, writableSackTrace);
    }
}

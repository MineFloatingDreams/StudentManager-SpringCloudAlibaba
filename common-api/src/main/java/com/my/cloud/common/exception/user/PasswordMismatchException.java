package com.my.cloud.common.exception.user;

import com.my.cloud.common.exception.ServiceException;

/**
 * 密码不匹配异常
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/27 19:55
 */
public class PasswordMismatchException extends ServiceException {
    public PasswordMismatchException() {
        super();
    }

    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordMismatchException(Throwable cause) {
        super(cause);
    }

    public PasswordMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableSackTrace) {
        super(message, cause, enableSuppression, writableSackTrace);
    }
}

package com.my.cloud.common.exception.code;

import com.my.cloud.common.exception.ServiceException;


/**
 * 验证码为空异常
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/26 15:05
 */
public class NullCodeException extends ServiceException {
    public NullCodeException() {
        super();
    }

    public NullCodeException(String message) {
        super(message);
    }

    public NullCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullCodeException(Throwable cause) {
        super(cause);
    }

    public NullCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableSackTrace) {
        super(message, cause, enableSuppression, writableSackTrace);
    }
}

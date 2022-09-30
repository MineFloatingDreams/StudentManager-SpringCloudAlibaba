package com.my.cloud.common.exception.code;

import com.my.cloud.common.exception.ServiceException;

/**
 * 验证码不匹配异常
 * @Author: zhangjiachen
 * @Date: 2022/9/26 15:07
 */
public class CodeMismatchException extends ServiceException {
    public CodeMismatchException() {
        super();
    }

    public CodeMismatchException(String message) {
        super(message);
    }

    public CodeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeMismatchException(Throwable cause) {
        super(cause);
    }

    public CodeMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableSackTrace) {
        super(message, cause, enableSuppression, writableSackTrace);
    }
}

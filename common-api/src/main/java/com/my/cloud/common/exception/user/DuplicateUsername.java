package com.my.cloud.common.exception.user;

import com.my.cloud.common.exception.ServiceException;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/23 15:49
 * 错误类，用来反馈重复的用户名错误信息
 */
public class DuplicateUsername extends ServiceException {
    public DuplicateUsername() {
        super();
    }

    public DuplicateUsername(String message) {
        super(message);
    }

    public DuplicateUsername(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateUsername(Throwable cause) {
        super(cause);
    }

    public DuplicateUsername(String message, Throwable cause, boolean enableSuppression, boolean writableSackTrace) {
        super(message, cause, enableSuppression, writableSackTrace);
    }
}

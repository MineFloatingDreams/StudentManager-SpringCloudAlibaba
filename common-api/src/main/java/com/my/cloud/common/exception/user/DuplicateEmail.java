package com.my.cloud.common.exception.user;

import com.my.cloud.common.exception.ServiceException;

/**
 * 重复邮箱异常类
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/23 16:55
 */
public class DuplicateEmail extends ServiceException {
    public DuplicateEmail() {
        super();
    }

    public DuplicateEmail(String message) {
        super(message);
    }

    public DuplicateEmail(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEmail(Throwable cause) {
        super(cause);
    }

    public DuplicateEmail(String message, Throwable cause, boolean enableSuppression, boolean writableSackTrace) {
        super(message, cause, enableSuppression, writableSackTrace);
    }
}

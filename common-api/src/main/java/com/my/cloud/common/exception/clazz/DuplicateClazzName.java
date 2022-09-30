package com.my.cloud.common.exception.clazz;

import com.my.cloud.common.exception.ServiceException;

/**
 * 班级名称重复异常
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/28 14:54
 */
public class DuplicateClazzName extends ServiceException {
    public DuplicateClazzName() {
        super();
    }

    public DuplicateClazzName(String message) {
        super(message);
    }

    public DuplicateClazzName(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateClazzName(Throwable cause) {
        super(cause);
    }

    public DuplicateClazzName(String message, Throwable cause, boolean enableSuppression, boolean writableSackTrace) {
        super(message, cause, enableSuppression, writableSackTrace);
    }
}

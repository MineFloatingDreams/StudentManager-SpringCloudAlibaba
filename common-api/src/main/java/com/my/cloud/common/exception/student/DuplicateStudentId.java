package com.my.cloud.common.exception.student;

import com.my.cloud.common.exception.ServiceException;

/**
 * 重复的学号异常类
 * @Author: zhangjiachen
 * @Date: 2022/9/28 16:23
 */
public class DuplicateStudentId extends ServiceException {
    public DuplicateStudentId() {
        super();
    }

    public DuplicateStudentId(String message) {
        super(message);
    }

    public DuplicateStudentId(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateStudentId(Throwable cause) {
        super(cause);
    }

    public DuplicateStudentId(String message, Throwable cause, boolean enableSuppression, boolean writableSackTrace) {
        super(message, cause, enableSuppression, writableSackTrace);
    }
}

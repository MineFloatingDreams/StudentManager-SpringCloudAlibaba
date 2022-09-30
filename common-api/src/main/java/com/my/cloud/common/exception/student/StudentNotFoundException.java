package com.my.cloud.common.exception.student;

import com.my.cloud.common.exception.ServiceException;

/**
 * 找不到学生信息异常
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/28 14:39
 */
public class StudentNotFoundException extends ServiceException {
    public StudentNotFoundException() {
        super();
    }

    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotFoundException(Throwable cause) {
        super(cause);
    }

    public StudentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableSackTrace) {
        super(message, cause, enableSuppression, writableSackTrace);
    }
}

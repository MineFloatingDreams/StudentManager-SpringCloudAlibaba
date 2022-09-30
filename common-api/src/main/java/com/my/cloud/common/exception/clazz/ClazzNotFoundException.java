package com.my.cloud.common.exception.clazz;

import com.my.cloud.common.exception.ServiceException;

/**
 * 找不到班级异常类
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/28 14:13
 */
public class ClazzNotFoundException extends ServiceException {
    public ClazzNotFoundException() {
        super();
    }

    public ClazzNotFoundException(String s) {
        super(s);
    }

    public ClazzNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClazzNotFoundException(Throwable cause) {
        super(cause);
    }
}

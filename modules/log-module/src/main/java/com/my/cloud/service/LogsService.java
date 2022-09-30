package com.my.cloud.service;

import java.util.Date;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/27 16:33
 */
public interface LogsService {
    void addLogs(Integer code, String message, Long userId);

    void addLogsByUsername(Integer code, String message, String username);
}

package com.my.cloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 日志服务接口
 * OpenFeign调用日志服务
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/27 21:29
 */
@Service
@FeignClient("spring-cloud-modules-log")
public interface LogsService {

    /**
     * 保存日志
     *
     * @param code    状态码
     * @param message 日志信息
     * @param userId  日志相关的用户id
     */
    @PostMapping("/logs/addLogs")
    void addLogs(@RequestParam("code") Integer code,
                 @RequestParam("message") String message,
                 @RequestParam("userId") Long userId);

    /**
     * 通过用户名保存日志
     *
     * @param code     状态码
     * @param message  日志信息
     * @param username 日志相关的用户名
     */
    @PostMapping("/logs/addLogsByUsername")
    void addLogsByUsername(@RequestParam("code") Integer code,
                           @RequestParam("message") String message,
                           @RequestParam("username") String username);

}

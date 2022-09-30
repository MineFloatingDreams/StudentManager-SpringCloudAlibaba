package com.my.cloud.controller;

import com.my.cloud.common.code.CommonResultCode;
import com.my.cloud.common.pojo.CommonResult;
import com.my.cloud.service.LogsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/27 16:43
 */
@RestController
@RequestMapping("/logs")
public class LogsController {

    @Resource
    private LogsService logsService;

    @PostMapping("/addLogs")
    public CommonResult<Object> addLogs(@RequestParam Integer code,
                                        @RequestParam String message,
                                        @RequestParam Long userId) {
        try {
            this.logsService.addLogs(code, message, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
        return new CommonResult<>(CommonResultCode.SUCCESS, "成功");
    }

    @PostMapping("/addLogsByUsername")
    public CommonResult<Object> addLogsByUsername(@RequestParam Integer code,
                                                  @RequestParam String message,
                                                  @RequestParam String username) {
        try {
            this.logsService.addLogsByUsername(code, message, username);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
        return new CommonResult<>(CommonResultCode.SUCCESS, "成功");
    }
}

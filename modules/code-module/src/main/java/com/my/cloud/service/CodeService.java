package com.my.cloud.service;


import org.springframework.messaging.Message;

/**
 * 验证码服务接口
 * @Author: zhangjiachen
 * @Date: 2022/9/26 12:31
 */

public interface CodeService {
    void sendCode(Message<String> message);
}

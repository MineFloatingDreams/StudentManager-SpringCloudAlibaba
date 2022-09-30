package com.my.cloud.service;

/**
 * 验证码服务接口
 * @Author: zhangjiachen
 * @Date: 2022/9/26 12:12
 */
public interface CodeService {
    /**
     * 发送验证码
     * @param email 邮箱账号
     */
    void sendCode(String email);
}

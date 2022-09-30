package com.my.cloud.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/26 13:18
 */
@SpringBootTest
public class CodeServiceTest {

    @Resource
    private CodeService codeService;

    @Test
    public void testSendCode() {
        this.codeService.sendCode("1121401344@qq.com");
    }


}

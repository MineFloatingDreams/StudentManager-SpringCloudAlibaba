package com.my.cloud.service;

import com.my.cloud.common.exception.code.CodeMismatchException;
import com.my.cloud.common.exception.code.NullCodeException;
import com.my.cloud.common.exception.user.DuplicateEmail;
import com.my.cloud.common.exception.user.DuplicateUsername;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户service的测试类
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/23 16:36
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    /**
     * 测试注册功能，重复运行可以测试重复的用户名是否会抛出异常
     */
    @Test
    public void testRegister() {

        try {
            userService.register("admin", "admin", "1121401344@qq.com", "123");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    @Test
    public void testLoginByPassword() {
        System.out.println(userService.loginByPassword("admin", "admin"));
    }
}

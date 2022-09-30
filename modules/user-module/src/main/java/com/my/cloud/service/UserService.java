package com.my.cloud.service;

import com.my.cloud.common.exception.code.CodeMismatchException;
import com.my.cloud.common.exception.code.NullCodeException;
import com.my.cloud.common.exception.user.DuplicateEmail;
import com.my.cloud.common.exception.user.DuplicateUsername;
import com.my.cloud.common.exception.user.PasswordMismatchException;
import com.my.cloud.common.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/23 15:24
 */
@Service
public interface UserService {
    /**
     * 用户注册方法，调用repository进行注册
     *
     * @param password 用户注册的密码
     * @param username 用户注册的用户名
     * @param email    用户注册的邮箱账号
     * @param code     用户注册用于验证的验证码
     *                 用户注册的ID为自动生成，不需要注入
     * @throws DuplicateUsername 如果有重复的用户名则会抛出此异常
     * @throws DuplicateEmail    如果有重复的邮箱账号则会抛出此异常
     */
    User register(String username, String password, String email, String code) throws DuplicateUsername, DuplicateEmail, CodeMismatchException, NullCodeException;

    /**
     * 用户登录方法，调用repository进行登录
     *
     * @param username 用户登录的用户名
     * @param password 用户登录的密码
     * @return 返回登录成功的用户信息
     */
    User loginByPassword(String username, String password);

    /**
     * 用户登录方法，调用repository进行登录
     *
     * @param email 用户登录的邮箱账号
     * @param code  用户登录用于验证的验证码
     * @return 返回登录成功的用户信息
     * @throws NullCodeException     如果验证码为空则会抛出此异常
     * @throws CodeMismatchException 如果验证码不匹配则会抛出此异常
     */
    User loginByEmail(String email, String code) throws NullCodeException, CodeMismatchException;

}

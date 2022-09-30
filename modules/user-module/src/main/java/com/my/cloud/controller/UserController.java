package com.my.cloud.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.my.cloud.common.code.CommonResultCode;
import com.my.cloud.common.code.UserResultCode;
import com.my.cloud.common.exception.code.CodeMismatchException;
import com.my.cloud.common.exception.code.NullCodeException;
import com.my.cloud.common.exception.user.DuplicateEmail;
import com.my.cloud.common.exception.user.DuplicateUsername;
import com.my.cloud.common.pojo.CommonResult;
import com.my.cloud.common.pojo.User;
import com.my.cloud.common.util.JWTUtils;
import com.my.cloud.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 用户的controller，用于处理前端请求
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/23 16:06
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册方法，用户在前台输入账户名、密码、验证码以及邮箱号后进行注册操作
     *
     * @param username 用户名
     * @param password 密码
     * @param email    邮箱号
     * @param code     验证码
     * @return 结果
     */
    @PostMapping("/register")
    public CommonResult<User> register(@RequestParam String username,
                                       @RequestParam String password,
                                       @RequestParam String email,
                                       @RequestParam String code) {
        //判空处理
        if ("".equals(username) || username == null) {
            return new CommonResult<>(CommonResultCode.NULL_DATA, "用户名为空，请检查输入");
        }
        if ("".equals(password) || password == null) {
            return new CommonResult<>(CommonResultCode.NULL_DATA, "密码为空，请检查输入");
        }
        if ("".equals(email) || email == null) {
            return new CommonResult<>(CommonResultCode.NULL_DATA, "邮箱号为空，请检查输入");
        }
        if ("".equals(code) || code == null) {
            return new CommonResult<>(CommonResultCode.NULL_DATA, "验证码为空，请检查输入");
        }
        //进行注册操作
        try {
            User register = this.userService.register(username, password, email, code);
            return new CommonResult<>(CommonResultCode.SUCCESS, "注册成功", register);
        } catch (NullCodeException e) {
            return new CommonResult<>(UserResultCode.NULL_CODE, "验证码已过期，请检查输入");
        } catch (CodeMismatchException e) {
            return new CommonResult<>(UserResultCode.CODE_MISMATCH, "验证码错误，请检查输入");
        } catch (DuplicateUsername e) {
            return new CommonResult<>(UserResultCode.DUPLICATE_USERNAME, "用户名重复，请重新输入");
        } catch (DuplicateEmail e) {
            return new CommonResult<>(UserResultCode.DUPLICATE_EMAIL, "邮箱号重复，请重新输入");
        } catch (Exception e) {
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }

    }

    /**
     * 前端处理用户名密码登录的方法
     *
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @return 返回状态码以及信息，如果登录成功则会附带数据
     */
    @PostMapping("/loginByPassword")
    public CommonResult<Object> loginByPassword(@RequestParam String username,
                                                @RequestParam String password) {
        //判空处理
        if ("".equals(username) || username == null) {
            return new CommonResult<>(CommonResultCode.NULL_DATA, "用户名为空，请检查输入");
        }
        if ("".equals(password) || password == null) {
            return new CommonResult<>(CommonResultCode.NULL_DATA, "密码为空，请检查输入");
        }
        //Service层处理业务
        User user;
        try {
            //查找用户信息
            User result = this.userService.loginByPassword(username, password);
            if (result == null) {
                return new CommonResult<>(UserResultCode.USER_NOT_FOUND, "用户名或密码错误，请检查输入");
            } else {
                //数据脱敏，只保留重要的数据，尤其是密码、手机号一类的不可以往前端走
                JSONObject jsonObject = new JSONObject();
                user = new User(
                        0L,
                        result.getUsername(),
                        "",
                        result.getEmail()
                );
                jsonObject.put("user", user);
                jsonObject.put("token", JWTUtils.geneJsonWebToken(result));
                return new CommonResult<>(CommonResultCode.SUCCESS, "登录成功", jsonObject);
            }
        } catch (Exception e) {
            //异常处理
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
    }

    /**
     * 前端处理邮箱验证码登录的方法
     *
     * @param email 用户输入的邮箱号
     * @param code  用户输入的验证码
     * @return 返回状态码以及信息，如果登录成功则会附带数据
     */
    @PostMapping("/loginByEmail")
    public CommonResult<Object> loginByEmail(@RequestParam String email,
                                             @RequestParam String code) {
        if ("".equals(email) || email == null) {
            return new CommonResult<>(CommonResultCode.NULL_DATA, "邮箱号为空，请检查输入");
        }
        if ("".equals(code) || code == null) {
            return new CommonResult<>(CommonResultCode.NULL_DATA, "验证码为空，请检查输入");
        }
        //Service层处理业务
        User user;
        try {
            User result = this.userService.loginByEmail(email, code);
            if (result == null) {
                return new CommonResult<>(UserResultCode.USER_NOT_FOUND, "账号或密码错误");
            } else {
                //数据脱敏，只保留重要的数据，尤其是密码、手机号一类的不可以往前端走
                JSONObject jsonObject = new JSONObject();
                user = new User(
                        0L,
                        result.getUsername(),
                        "",
                        result.getEmail()
                );
                jsonObject.put("token", JWTUtils.geneJsonWebToken(result));
                jsonObject.put("user", user);
                return new CommonResult<>(CommonResultCode.SUCCESS, "登录成功", jsonObject);
            }
        } catch (NullCodeException e) {
            return new CommonResult<>(UserResultCode.NULL_CODE, "验证码已过期，请检查输入");
        } catch (CodeMismatchException e) {
            return new CommonResult<>(UserResultCode.CODE_MISMATCH, "验证码错误，请检查输入");
        } catch (Exception e) {
            //异常处理
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
    }

    @RequestMapping("/validateToken")
    public CommonResult<User> validateToken(@RequestParam String token) {
        Claims claims = JWTUtils.checkJWT(token);
        User user = new User();
        if (claims != null) {
            user.setUsername(claims.get("username", String.class));
            return new CommonResult<>(CommonResultCode.SUCCESS, "验证成功", user);
        } else {
            return new CommonResult<>(CommonResultCode.UNAUTHORIZED, "token无效");
        }
    }
}

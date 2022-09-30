package com.my.cloud.common.code;

/**
 * 用于存放用户的一些错误代码
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/23 16:14
 */
public class UserResultCode {
    // 重复的用户名
    public static Integer DUPLICATE_USERNAME = 2001;
    // 重复的邮箱号
    public static Integer DUPLICATE_EMAIL = 2002;
    //验证码为空
    public static Integer NULL_CODE = 2003;
    //验证码不一致
    public static Integer CODE_MISMATCH = 2004;

    public static Integer USER_NOT_FOUND = 2005;
}

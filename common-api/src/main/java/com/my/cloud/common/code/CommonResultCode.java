package com.my.cloud.common.code;

/**
 * 用于存放一些常见的异常代码
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/23 16:17
 */
public class CommonResultCode {

    //操作成功
    public static Integer SUCCESS = 200;

    //数据为空
    public static Integer NULL_DATA = 1001;

    //未知错误
    public static Integer UNKNOWN_ERROR = 500;

    //Token为空
    public static Integer TOKEN_NULL = 501;

    //Token过期
    public static Integer TOKEN_EXPIRED = 502;

    //Token无效
    public static Integer UNAUTHORIZED = 503;
}

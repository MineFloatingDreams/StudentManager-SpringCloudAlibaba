package com.my.cloud.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 通用返回结果
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/7 16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonResult<T> {
    // 返回码
    private Integer code;
    // 返回信息
    private String message;
    // 返回数据
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}

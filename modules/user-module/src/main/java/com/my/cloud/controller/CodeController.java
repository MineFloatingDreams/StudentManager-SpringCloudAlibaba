package com.my.cloud.controller;

import com.my.cloud.common.code.CommonResultCode;
import com.my.cloud.common.pojo.CommonResult;
import com.my.cloud.service.CodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/26 13:31
 */
@Slf4j
@RestController
@RequestMapping("/code")
public class CodeController {
    @Resource
    private CodeService codeService;

    @GetMapping("/send/{email}")
    public CommonResult<String> sendCode(@PathVariable("email") String email) {
        if ("".equals(email) || email == null) {
            return new CommonResult<>(CommonResultCode.NULL_DATA, "邮箱号为空，请检查输入");
        }
        try {
            //发送验证码
            this.codeService.sendCode(email);
            return new CommonResult<>(CommonResultCode.SUCCESS, "成功");
        } catch (Exception e) {
            //异常处理
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
    }
}

package com.my.cloud.aspect;

import com.my.cloud.common.pojo.CommonResult;
import com.my.cloud.common.pojo.User;
import com.my.cloud.service.LogsService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/27 16:53
 */
@Aspect
@Component
@Slf4j
public class UserResultAspect {

    @Resource
    private LogsService logsService;

    /**
     * 将所有controller层级的方法 作为切入点
     */
    @Pointcut("execution(public * com.my.cloud.controller..*.*(..))")
    public void handlePlaceholder() {
    }
/*
    @AfterReturning(returning = "ret", pointcut = "handlePlaceholder()")
    public void doAfterReturning(Object ret) {
        System.out.println("ResultAspect result");
        if (ret instanceof CommonResult) {
            CommonResult<User> result = (CommonResult<User>) ret;
            System.out.print(result);
            if (result.getData() != null) {
                logsService.addLogs(result.getCode(), result.getMessage(), result.getData().getId());
            }
        }
    }
 */

    /**
     * 环绕通知
     *
     * @param joinPoint 切入点
     * @return Object
     * @throws Throwable 异常
     */
    @Around("handlePlaceholder()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        String[] params = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        CommonResult result = (CommonResult) joinPoint.proceed(args);
        log.info("请求参数名称为" + Arrays.toString(params));
        log.info("请求参数为{}", args);
        //如果返回值的data不为空，就记录日志
        if (result.getData() == null) {
            //范围值data为空，查找参数中是否有username
            int index = Arrays.binarySearch(params, "username");
            //如果有username，就记录日志
            if (index >= 0) {
                //调用日志记录方法
                String username = (String) args[index];
                this.logsService.addLogsByUsername(result.getCode(), result.getMessage(), username);
            }
        } else {
            //如果返回值的data为user类型，就记录日志
            if (result.getData() instanceof User) {
                User u = (User) result.getData();
                //调用日志记录方法
                this.logsService.addLogs(result.getCode(), result.getMessage(), u.getId());
            }
        }
        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }
}

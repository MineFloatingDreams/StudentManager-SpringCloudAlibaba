package com.my.cloud.aspect;

import com.my.cloud.common.pojo.CommonResult;
import com.my.cloud.common.pojo.User;
import com.my.cloud.common.util.JWTUtils;
import com.my.cloud.service.LogsService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/27 16:53
 */
@Aspect
@Component
@Slf4j
public class StudentResultAspect {

    @Resource
    private LogsService logsService;

    /**
     * 将所有controller层级的方法 作为切入点
     */
    @Pointcut("execution(public * com.my.cloud.controller..*.*(..))")
    public void handlePlaceholder() {
    }

    /**
     * 环绕通知
     * <p>
     * 区别于User当中的aop，此处的请求参数全部都带上了对应的username作为请求参数，因此在此处直接添加即可
     *
     * @param joinPoint 切入点
     * @return Object
     * @throws Throwable 异常
     */
    @Around("handlePlaceholder()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        assert sra != null;
        HttpServletRequest request = sra.getRequest();

        String token = request.getHeader("token");

        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        CommonResult result = (CommonResult) joinPoint.proceed(args);

        //调用日志记录方法
        Long userId = JWTUtils.getId(token);
        log.info("userId:{}", userId);
        if (userId != null) {
            this.logsService.addLogs(result.getCode(), result.getMessage(), userId);
        }
        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }
}

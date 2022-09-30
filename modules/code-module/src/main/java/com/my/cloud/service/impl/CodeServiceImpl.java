package com.my.cloud.service.impl;

import com.alibaba.csp.sentinel.util.function.Consumer;
import com.my.cloud.message.MyMessageChannel;
import com.my.cloud.service.CodeService;
import com.my.cloud.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现类
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/26 12:40
 */
@Service
@Slf4j
@EnableBinding(MyMessageChannel.class)
public class CodeServiceImpl implements CodeService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 发送验证码
     *
     * @param message 接收到的消息
     */
    @Override
    @StreamListener(MyMessageChannel.EMAIL_IN)
    public void sendCode(Message<String> message) {
        //获取邮箱账号
        String email = message.getPayload();
        //日志打印邮箱
        log.info(email);
        try {
            //发送验证码
            MailUtil.send_mail(email);
            //将验证码存入redis
            redisTemplate.opsForValue().set(email, MailUtil.getCode() + "", 5, TimeUnit.MINUTES);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

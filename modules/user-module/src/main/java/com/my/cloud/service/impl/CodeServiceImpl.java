package com.my.cloud.service.impl;

import com.my.cloud.channel.MyMessageChannel;
import com.my.cloud.service.CodeService;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @Author: zhangjiachen
 * @Date: 2022/9/26 12:13
 */
@Service
@EnableBinding(MyMessageChannel.class) //定义消息的推送管道
public class CodeServiceImpl implements CodeService {

    @Resource
    private MessageChannel emailOut;//消息发送管道

    /**
     * 发送验证码
     * 使用RabbitMQ实现消息的发送
     * 自定义了一个消息管道，用于发送消息
     * 但是该注解已经弃用，不推荐使用
     *
     * @param email 邮箱账号
     */
    @Override
    public void sendCode(String email) {
        this.emailOut.send(MessageBuilder.withPayload(email).build());
    }
}

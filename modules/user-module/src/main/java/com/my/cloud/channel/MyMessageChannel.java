package com.my.cloud.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MyMessageChannel {

    String EMAIL_OUT = "emailOut";


    @Output(EMAIL_OUT)
    MessageChannel emailOut();


}
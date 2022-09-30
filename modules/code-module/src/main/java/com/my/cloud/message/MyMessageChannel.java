package com.my.cloud.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;


public interface MyMessageChannel {

    String EMAIL_IN = "emailIn";


    @Input(EMAIL_IN)
    SubscribableChannel emailIn();

}
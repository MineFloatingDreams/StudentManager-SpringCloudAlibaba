package com.my.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/28 12:26
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class StudentPayment {
    public static void main(String[] args) {
        SpringApplication.run(StudentPayment.class, args);
    }
}

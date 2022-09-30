package com.my.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/23 15:07
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserProvider {
    public static void main(String[] args) {
        SpringApplication.run(UserProvider.class, args);
    }
}

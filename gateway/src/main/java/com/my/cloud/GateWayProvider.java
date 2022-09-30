package com.my.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/26 16:29
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayProvider {
    public static void main(String[] args) {
        SpringApplication.run(GateWayProvider.class, args);
    }
}

package com.leyou;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * service微服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LeyouItemService {
    public static void main(String[] args) {
        SpringApplication.run(LeyouItemService.class, args);
    }
}

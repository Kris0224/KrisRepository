package com.leyou;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * service微服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.mapper")//添加mapper的扫描
public class LeyouItemService {
    public static void main(String[] args) {
        SpringApplication.run(LeyouItemService.class, args);
    }
}

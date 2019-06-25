package com.leyou;


//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * service微服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
//@MapperScan("com.leyou.mapper")//添加springboot的mapper的扫描
@MapperScan("com.leyou.mapper")//添加通用mapper的mapper的扫描
public class LeyouItemService {
    public static void main(String[] args) {
        SpringApplication.run(LeyouItemService.class, args);
    }
}
package com.kris.feign;


import com.kris.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service" ,fallback = UserFeignClientFallback.class)
//填写服务名 feign根据服务名从eureka拉取该服务名对应的服务列表 底层使用ribbon负载均衡获取服务
//fallback 的值为其接口的实现类的class对象
public interface UserFeignClient {

    @GetMapping("user/{id}")//根据获取的服务 根据该请求路径 和参数 远程调用
    User queryUserById(@PathVariable("id") long id);

}

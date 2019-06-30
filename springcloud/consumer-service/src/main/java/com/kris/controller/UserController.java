package com.kris.controller;


import com.kris.feign.UserFeignClient;
import com.kris.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "queryByIdFallBack")//实现默认的fallback
public class UserController {

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
    *查询返回用户
    **/
 /*   @GetMapping("/{id}")
    public User queryUserById (@PathVariable("id") long id){

        String url = "http://localhost:8081/user/" + id;
        User user = restTemplate.getForObject(url, User.class);
        return user;

    }*/


/*    @GetMapping("/{id}")
*//*    public User queryUserById (@PathVariable("id") long id){

//      根据服务id(spring.application.name)，获取服务实例列表
        List<ServiceInstance> instanceList = discoveryClient.getInstances("user-service");

//      取出实例
        ServiceInstance instance = instanceList.get(0);

//      从实例获取host和port
        String url = String.format("http://%s:%s/user/%s", instance.getHost(), instance.getPort(), id);

//      search根据url发送远程请求 并且获取返回值
        User user = restTemplate.getForObject(url, User.class);

        return user;

    }*/

/*
    */
/**
     * 开启负载均衡
     * @param id
     * @return
     *//*

    @GetMapping("/{id}")
    public User queryUserById (@PathVariable("id") long id){
//   不再手动获取ip和端口，而是直接通过服务名称调用
        String url = "http://user-service/user/" + id;
        User user = restTemplate.getForObject(url, User.class);
        return user;

    }
*/


    /**
     * 开启负载均衡
     * 开启熔断器
     * @param id
     * @return
     */
 /*   @GetMapping("/{id}")
//  @HystrixCommand 使用默认的降级逻辑的fallback方法 绑定在类上
    @HystrixCommand(fallbackMethod = "queryByIdFallBack")//声明一个降级逻辑fallback的方法,绑定在但一方法上
    public User queryUserById (@PathVariable("id") long id){
//   不再手动获取ip和端口，而是直接通过服务名称调用
        String url = "http://user-service/user/" + id;
        User user = restTemplate.getForObject(url, User.class);
        return user;

    }*/

    /**
     * 熔断方法
     * @param id
     * @return
     */
    public String queryByIdFallBack(Long id){
        return "对不起，网络太拥挤了！";
    }


    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 使用fegin结合springmvc的注解伪装发送远程调用
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") long id){
        return userFeignClient.queryUserById(id);
    }

}

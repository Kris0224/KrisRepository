package com.kris.feign;

import com.kris.pojo.User;
import org.springframework.stereotype.Component;

/**
 * 熔断接口的实现类
 */
@Component
public class UserFeignClientFallback implements UserFeignClient {

    /**
     * 书写熔断的方法
     * @param id
     * @return
     */
    @Override
    public User queryUserById(long id) {

        User user = new User();

        user.setAge(null);
        user.setId(0);
        user.setSex(null);
        user.setUsername("unknow username");

        return user;
    }
}

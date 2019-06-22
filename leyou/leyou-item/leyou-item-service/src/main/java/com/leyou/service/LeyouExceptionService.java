package com.leyou.service;


import com.leyou.common.exception.LeyouException;
import com.leyou.common.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;


@Service
public class LeyouExceptionService {

    public User testExcepService(User user){
        /**
         * 判断请求参数是否包含username
         * 否则抛异常
         */
        if (StringUtils.isEmpty(user.getUsername())){
            throw new LeyouException(400, "用户名不存在");
        }

        return user;
    }
}

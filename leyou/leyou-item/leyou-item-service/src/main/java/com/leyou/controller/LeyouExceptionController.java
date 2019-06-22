package com.leyou.controller;


import com.leyou.common.pojo.User;
import com.leyou.service.LeyouExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * 测试自定义异常处理的controller
 */
@RestController
public class LeyouExceptionController {

    @Autowired
    private LeyouExceptionService leyouExceptionService;

    @PostMapping("exception")
    public ResponseEntity<User> leyouExcep(User user){

        User resultUser = leyouExceptionService.testExcepService(user);

        return ResponseEntity.status(201).body(resultUser);
    }
}

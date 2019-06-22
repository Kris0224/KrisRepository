package com.leyou.controller;


import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LeyouException;
import com.leyou.common.pojo.User;
import com.leyou.service.LeyouExceptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试自定义异常处理的controller
 */
@RestController
public class LeyouEnumExceptionController {

    @Autowired
    private LeyouExceptionService leyouExceptionService;

    @PostMapping("enum")
    public ResponseEntity<User> leyouExcep(User user){

        if (StringUtils.isBlank(user.getUsername())) {
            throw new LeyouException(ExceptionEnums.APPLICATION_NOT_FOUND);
        }

        User resultUser = leyouExceptionService.testExcepService(user);

        return ResponseEntity.status(201).body(resultUser);
    }
}

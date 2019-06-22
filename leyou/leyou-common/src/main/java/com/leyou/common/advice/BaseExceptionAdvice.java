package com.leyou.common.advice;


import com.leyou.common.exception.LeyouException;
import com.leyou.common.pojo.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 自定义枚举异常结果controller的异常处理
 */
@ControllerAdvice
@Slf4j
public class BaseExceptionAdvice {

   /* @ExceptionHandler(LeyouException.class)//需要处理的异常
    public ResponseEntity<String> handleException(LeyouException e){
        e.printStackTrace();
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }*/

    /**
     * 使用了exceptionresult实体类封装异常返回信息
     * @param e
     * @return
     */
    @ExceptionHandler(LeyouException.class)//需要处理的异常
    public ResponseEntity<ExceptionResult> handleException(LeyouException e){
        e.printStackTrace();
        return ResponseEntity.status(e.getStatus()).body(new ExceptionResult(e));
    }
}

package com.leyou.common.exception;


import com.leyou.common.enums.ExceptionEnums;
import lombok.Getter;

/**
 * leyou的异常
 */
@Getter
public class LeyouException extends RuntimeException {

    /**
     * 异常状态码
     */
    private int status;

    /**
     * 含message的构造函数
     * @param message
     */
    public LeyouException(int status,String message) {
        super(message);
        this.status = status;
    }

    /**
     * 含消息和异常类的转换
     * @param message
     * @param cause
     */
    public LeyouException(int status,String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    /**
     * 含枚举参数的构造函数
     * @param em
     */
    public LeyouException(ExceptionEnums em) {
        super(em.getMessage());
        this.status = em.getStatus();
    }

    public LeyouException(ExceptionEnums em, Throwable cause) {
        super(em.getMessage(), cause);
        this.status = em.getStatus();
    }




}

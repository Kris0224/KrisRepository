package com.leyou.common.pojo;

import com.leyou.common.exception.LeyouException;
import org.joda.time.DateTime;


/**
 * 封装异常结果的实体类
 */
public class ExceptionResult {

    private int status;
    private String message;
    private String timestamp;

    public ExceptionResult(LeyouException e) {
        this.status = e.getStatus();
        this.message = e.getMessage();
        this.timestamp = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }

}

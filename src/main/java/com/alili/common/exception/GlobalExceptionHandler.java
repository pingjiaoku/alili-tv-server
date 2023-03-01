package com.alili.common.exception;

import com.alili.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public R<String> doBusinessException(BusinessException e) {
        log.error(e.getMessage());

        return R.error(e.getMessage());
    }

    @ExceptionHandler(SystemException.class)
    public R<String> doSystemException(SystemException e) {
        log.error(e.getMessage());

        return R.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R<String> doException(Exception e){
        // 记录日志
        // 发送消息给运维
        // 发送邮件给开发人员,ex对象发送给开发人工
        e.printStackTrace();
        return R.error("系统繁忙, 请稍后再试");
    }

}

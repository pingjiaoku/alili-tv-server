package com.alili.common.exception;

/**
 * 业务异常，（由用户异常操作引起）
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}
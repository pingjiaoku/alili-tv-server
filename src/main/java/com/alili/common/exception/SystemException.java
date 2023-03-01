package com.alili.common.exception;

/**
 * 系统异常，（由程序异常逻辑引起）
 */
public class SystemException extends RuntimeException {

    public SystemException(String message) {
        super(message);
    }

}

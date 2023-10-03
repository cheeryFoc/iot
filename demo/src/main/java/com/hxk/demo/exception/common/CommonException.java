package com.hxk.demo.exception.common;

/**
 * @projectName: demo
 * @package: com.hxk.demo.exception.common
 * @className: CommonException
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class CommonException extends RuntimeException{
    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMsg());
    }
}

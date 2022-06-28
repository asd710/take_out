package com.hm.takeout.boot.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 * ControllerAdvice扫描指定注解中的异常
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandle {
    /**
     * 异常处理方法
     * ExceptionHandler接受括号里的异常，接受后执行方法
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandle(SQLIntegrityConstraintViolationException ex){
        log.info(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")){
            String[] split= ex.getMessage().split(" ");
            String msg = split[2] + "已经存在";
            return R.error(msg);
        }
        return R.error("添加失败");
    }
    /**
     * 异常处理方法
     * ExceptionHandler接受括号里的异常，接受后执行方法
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandle(CustomException ex){
        log.info(ex.getMessage());
        return R.error(ex.getMessage());
    }
}

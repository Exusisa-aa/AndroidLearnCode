package com.restaurant.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Global Exception Handler
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle SQL integrity constraint violation
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + " already exists";
            return Result.error(msg);
        }
        return Result.error("Unknown Error");
    }

    /**
     * Handle custom exceptions
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public Result<String> exceptionHandler(CustomException ex){
        log.error(ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * Handle general exceptions
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception ex){
        log.error(ex.getMessage());
        return Result.error(ex.getMessage());
    }
}

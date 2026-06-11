package com.film.backend.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public Result<String> handleRuntimeException(RuntimeException e) {
        log.error("Runtime exception: ", e);
        return Result.error(500, e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("System exception: ", e);
        return Result.error(500, "System Error, please try again later.");
    }
}

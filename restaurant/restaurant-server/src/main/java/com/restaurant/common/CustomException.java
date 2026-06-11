package com.restaurant.common;

/**
 * Custom Exception
 */
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}

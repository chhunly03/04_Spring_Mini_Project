package com.khrd.spring_boot_mini_project.exception;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException (String message){
        super(message);
    }
}

package com.example.Tuan8.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleBlank(ConstraintViolationException e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getConstraintViolations().iterator().next().getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFound(EntityNotFoundException e){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "require [UPDATE, DELETE, CREATE, READ]");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException e) {
        String message = e.getMostSpecificCause().getMessage(); // Lấy lỗi SQL gốc
        if (message.contains("role.UKiubw515ff0ugtm28p8g3myt0h")) {
            return new ErrorResponse(HttpStatus.CONFLICT.value(), "role name already exists!");
        }else if (message.contains("role_permission.UK4v9rc4dhd2u79uyr9grgnx07m")){
            return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "role already has this permission!");
        }
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}

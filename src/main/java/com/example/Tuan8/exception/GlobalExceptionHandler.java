package com.example.Tuan8.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleBlank(ConstraintViolationException e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getConstraintViolations().iterator().next().getMessage());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentials(BadCredentialsException e){
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFound(EntityNotFoundException e){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message = e.getMostSpecificCause().getMessage();
        // Trường role_id nhập String
        if (message.contains("not a valid `int` value")) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid number format: Expected an integer!");
        }
        // Nhập giá trị không phải giá trị của enum
        else if (message.contains("[UPDATE, DELETE, CREATE, READ]")) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid permission! Accepted values: [UPDATE, DELETE, CREATE, READ]");
        }

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Malformed JSON request!");
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException e) {
        String message = e.getMostSpecificCause().getMessage();
        // Nhập trùng department
        if (message.contains("department.UKiubw515ff0ugtm28p8g3myt0h")) {
            return new ErrorResponse(HttpStatus.CONFLICT.value(), "department name already exists!");
        }
        // Nhập trùng permission
        else if (message.contains("role_permission.UK4v9rc4dhd2u79uyr9grgnx07m")){
            return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "department already has this permission!");
        }
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }


    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ErrorResponse handleForbiden(HttpClientErrorException.Forbidden e){
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}

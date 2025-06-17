package com.mvp.customerprocessor.exception.advice;

import com.mvp.customerprocessor.exception.CustomerProcessorRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomerProcessorRuntimeException.class)
    public ExceptionDto handleResourceNotFound(CustomerProcessorRuntimeException ex) {
        log.error("CustomerProcessorRuntimeException: ", ex);
        return new ExceptionDto(Map.of("CustomerProcessorRuntimeException", ex.getMessage()), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionDto handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException: ", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error(ex.getMessage());
        return new ExceptionDto(errors, LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionDto handleGeneralException(Exception ex) {
        log.error("Exception: ", ex);
        return new ExceptionDto(Map.of("Exception", ex.getMessage()), LocalDateTime.now());
    }
}
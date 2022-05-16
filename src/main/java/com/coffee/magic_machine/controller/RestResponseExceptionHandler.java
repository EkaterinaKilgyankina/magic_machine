package com.coffee.magic_machine.controller;

import com.coffee.magic_machine.exception.ErrorMessage;
import com.coffee.magic_machine.exception.IngredientLackException;
import com.coffee.magic_machine.exception.NotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ErrorMessage handleExceptions(NotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ErrorMessage handleExceptions(IngredientLackException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorMessage("ingredient lack - " + ex.getIngredientType());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        if (ex.getCause() instanceof JsonMappingException) {
            JsonMappingException exception = (JsonMappingException) ex.getCause();
            final String message = exception.getPath()
                    .stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .map(e -> String.format("wrong field format - [%s ]", e))
                    .orElse(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorMessage(message));
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorMessage handleExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorMessage(ex.getMessage());
    }
}

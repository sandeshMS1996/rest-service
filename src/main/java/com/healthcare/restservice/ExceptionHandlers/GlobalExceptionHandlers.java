package com.healthcare.restservice.ExceptionHandlers;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandlers {

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler({org.hibernate.PropertyValueException.class})
    public void DuplicateException(PropertyValueException e) {
        e.printStackTrace();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ConstraintViolationException.class})
    public void Duplicate(ConstraintViolationException e) {
        e.printStackTrace();
    }

}

package com.company.portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<EmployeeErrorResponse> handleException(EmployeeNotFoundException e) {
        EmployeeErrorResponse errorResponse = new EmployeeErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<EmployeeErrorResponse> handleException(EmployeeAlreadyExistsException e) {
        EmployeeErrorResponse errorResponse = new EmployeeErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleException(Exception e) {
        EmployeeErrorResponse errorResponse = new EmployeeErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

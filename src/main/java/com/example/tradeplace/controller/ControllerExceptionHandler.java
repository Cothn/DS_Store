package com.example.tradeplace.controller;


import com.example.tradeplace.repository.exceptions.DBModificationException;
import com.example.tradeplace.repository.exceptions.DBFoundException;
import com.example.tradeplace.repository.exceptions.RepositoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DBFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String>  handleDBFoundException(DBFoundException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DBModificationException.class)
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public ResponseEntity<String> handleDBModificationException(DBModificationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String>  repositoryException(RepositoryException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

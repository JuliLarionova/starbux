package com.bstlr.starbux.web;

import com.bstlr.starbux.common.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    @ExceptionHandler(ClientException.class)
    protected ResponseEntity<String> handleClientException(ClientException ex) {
        return ResponseEntity.badRequest().body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<String> handleSQLException(SQLException ex) {
        return ResponseEntity.badRequest().body(ex.getLocalizedMessage());
    }
}
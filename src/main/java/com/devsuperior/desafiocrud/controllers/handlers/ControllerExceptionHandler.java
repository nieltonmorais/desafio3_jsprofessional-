package com.devsuperior.desafiocrud.controllers.handlers;

import com.devsuperior.desafiocrud.DTO.CustomError;
import com.devsuperior.desafiocrud.services.exceptions.DatabaseException;
import com.devsuperior.desafiocrud.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
/*
    Em uma classe com a annotation @ControllerAdvice, podemos definir tratamentos globais para
    exceções específicas, sem precisar ficar colocando try-catch em várias partes do código.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(Instant.now(), 404, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> databaseException(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), 404, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }


}

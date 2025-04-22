package com.genieLogiciele.sprint.exception.handler;

import com.genieLogiciele.sprint.exception.EntityAlreadyExist;
import com.genieLogiciele.sprint.exception.EntityNotFound;
import com.genieLogiciele.sprint.exception.SharedError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandlerAppException {
    @ExceptionHandler(value = {EntityAlreadyExist.class})
    public ResponseEntity<Object> entityalreadyexist(EntityAlreadyExist ex)
    {
      SharedError errorShared = SharedError.builder()
                .code(409)
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(errorShared, HttpStatus.CONFLICT);

    }
    @ExceptionHandler(value = {EntityNotFound.class})
    public ResponseEntity<Object> entitynotfound(EntityNotFound ex)
    {
        SharedError errorShared = SharedError.builder()
                .code(404)
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(errorShared, HttpStatus.NOT_FOUND);

    }



    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> HandleMethodArgumentNotValid(MethodArgumentNotValidException ex)
    {
        Map<String,String> errors =new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
        return new ResponseEntity<>(errors,new HttpHeaders(),HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

package com.code4fun.book.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public String resourceNotFoundException(ResourceNotFoundException ex) {
    return ex.getMessage();
  }
}

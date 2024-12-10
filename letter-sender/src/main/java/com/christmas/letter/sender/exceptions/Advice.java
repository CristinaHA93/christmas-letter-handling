package com.christmas.letter.sender.exceptions;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class Advice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(InvalidDataException.class)
  public ResponseEntity<Object> handleInvalidDataException(
      InvalidDataException ex, WebRequest request) {
    return handleExceptionInternal(ex, HttpStatus.BAD_REQUEST, request);
  }

  public ResponseEntity<Object> handleExceptionInternal(Exception ex, HttpStatus status, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, status);
  }


}

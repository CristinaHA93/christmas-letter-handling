package com.christmas.letter.sender.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class Advice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(
      MethodArgumentNotValidException ex, WebRequest request) {

    return handleExceptionInternal(ex, HttpStatus.BAD_REQUEST,request);
  }

  public ResponseEntity<Object> handleExceptionInternal(Exception ex, HttpStatus status, WebRequest request) {
    MethodArgumentNotValidException notValidException = (MethodArgumentNotValidException) ex;
    List<ObjectError> errors = notValidException.getBindingResult().getAllErrors();

    List<String> reasons = new ArrayList<>();
    for (ObjectError error: errors) {
      reasons.add(error.getDefaultMessage());
    }

    ErrorDetails errorDetails = new ErrorDetails(new Date(), "The validation has failed!", request.getDescription(false), reasons);
    return new ResponseEntity<>(errorDetails, status);
  }

}

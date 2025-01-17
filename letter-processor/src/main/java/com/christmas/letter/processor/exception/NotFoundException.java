package com.christmas.letter.processor.exception;

public class NotFoundException extends RuntimeException{
  public NotFoundException(String id) {
    super(String.format("Email %s not found", id));
  }

}

package com.christmas.letter.processor.exception;

public class NotFoundException extends RuntimeException{
  public NotFoundException(String id) {
    super(String.format("Id %s not found", id));
  }

}

package com.christmas.letter.processor.exception;

public class DeserializationException extends RuntimeException{
  public DeserializationException(String message){
    super(message);
  }
}

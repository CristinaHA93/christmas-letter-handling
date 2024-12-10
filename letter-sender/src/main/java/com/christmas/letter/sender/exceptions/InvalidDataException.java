package com.christmas.letter.sender.exceptions;

public class InvalidDataException extends RuntimeException{

  public InvalidDataException(String errorMessage) {
    super(errorMessage);
  }

}

package com.christmas.letter.sender.exceptions;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {

  private Date timestamp;

  private String message;

  private String details;

  private List<String> reasons;

}

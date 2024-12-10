package com.christmas.letter.sender.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Letter {

  private String email;
  private String name;
  private String wishes;
  private Location location;

}

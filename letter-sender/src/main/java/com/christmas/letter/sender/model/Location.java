package com.christmas.letter.sender.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {

  @NotNull
  private double latitude;
  @NotNull
  private double longitude;

}

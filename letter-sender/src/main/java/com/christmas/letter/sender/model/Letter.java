package com.christmas.letter.sender.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Letter {

  @NotNull(message="Email required")
  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "The email is invalid!")
  private String email;
  @NotEmpty(message = "Name required")
  private String name;
  @NotEmpty(message = "Please add your wishes!")
  private String wishes;
  @Valid
  private Location location;

}

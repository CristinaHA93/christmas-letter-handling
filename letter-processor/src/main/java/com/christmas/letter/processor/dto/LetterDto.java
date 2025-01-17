package com.christmas.letter.processor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LetterDto {

  @NotBlank(message="Email required")
  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "The email is invalid!")
  private String email;
  @NotBlank(message = "Name required")
  private String name;
  @NotBlank(message = "Please add your wishes!")
  private String wishes;
  @Valid
  @NotNull(message = "Address is required")
  private LocationDto location;

}

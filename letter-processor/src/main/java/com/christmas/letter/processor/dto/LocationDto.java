package com.christmas.letter.processor.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {

  @DecimalMin(value = "-90.0", inclusive = true, message = "Latitude must be between -90 and 90.")
  @DecimalMax(value = "90.0", inclusive = true, message = "Latitude must be between -90 and 90.")
  private Double latitude;

  @DecimalMin(value = "-90.0", inclusive = true, message = "Longitude must be between -90 and 90.")
  @DecimalMax(value = "90.0", inclusive = true, message = "Longitude must be between -90 and 90.")
  private Double longitude;

}

package com.christmas.letter.processor.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Location {

  @DynamoDBAttribute(attributeName = "Latitude")
  private Double latitude;

  @DynamoDBAttribute(attributeName = "Longitude")
  private Double longitude;

}

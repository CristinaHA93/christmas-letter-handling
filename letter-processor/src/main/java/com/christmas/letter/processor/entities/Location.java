package com.christmas.letter.processor.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;

@Data
@DynamoDBDocument
public class Location {

  private Integer id;

  private Double latitude;

  private Double longitude;

}

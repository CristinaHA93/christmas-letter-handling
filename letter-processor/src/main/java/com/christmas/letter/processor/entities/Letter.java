package com.christmas.letter.processor.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "SantaChristmasLetters")
public class Letter {

    @DynamoDBHashKey(attributeName = "Email")
    private String email;

    @DynamoDBAttribute(attributeName = "Name")
    private String name;

    @DynamoDBAttribute(attributeName = "Wishes")
    private String wishes;

    @DynamoDBTypeConvertedJson
    @DynamoDBAttribute(attributeName = "Location")
    private Location location;

  }


package com.christmas.letter.sender.service;

import com.christmas.letter.sender.exceptions.LetterValidator;
import com.christmas.letter.sender.model.Letter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
@RequiredArgsConstructor
public class LetterService {

  @NonNull
  private final SnsClient snsClient;
  @NonNull
  private final LetterValidator letterValidator;
  private final ObjectMapper objectMapper;

  private final String snsTopicArn = "arn:aws:sns:us-east-1:000000000000:ChristmasLetterSns";

  public String publishLetter(Letter letter) {

    try{

      this.letterValidator.validate(letter);

      String message = objectMapper.writeValueAsString(letter);

      PublishRequest publishRequest = PublishRequest.builder()
          .message(message)
          .topicArn(snsTopicArn)
          .build();

      snsClient.publish(publishRequest);

      return "Message published successfully";
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error publish letter", e);
    }

  }

}


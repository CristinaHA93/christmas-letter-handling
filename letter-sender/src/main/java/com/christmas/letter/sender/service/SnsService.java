package com.christmas.letter.sender.service;

import com.christmas.letter.sender.exceptions.InvalidDataException;
import com.christmas.letter.sender.model.Letter;

import java.util.regex.Pattern;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
@RequiredArgsConstructor
public class SnsService {

  @NonNull
  private final SnsClient snsClient;
  private final String snsTopicArn = "arn:aws:sns:us-east-1:000000000000:ChristmasLetterSns";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$");

  public String publishLetter(Letter letter) {

      if (letter.getEmail() == null || !EMAIL_PATTERN.matcher(letter.getEmail()).matches()) {
        throw new InvalidDataException("Invalid email address provided: " + letter.getEmail());
      }

      String message = buildLetterMessage(letter);

      PublishRequest publishRequest = PublishRequest.builder()
          .message(message)
          .topicArn(snsTopicArn)
          .build();

      snsClient.publish(publishRequest);

      return "Message published successfully";

  }

  private String buildLetterMessage(Letter letter) {

    return String.format("Letter from %s (%s)\nWishlist: %s\nLocation: %f, %f",
        letter.getName(),
        letter.getEmail(),
        letter.getWishes(),
        letter.getLocation().getLatitude(),
        letter.getLocation().getLongitude());
  }

}


package com.christmas.letter.sender.service;

import com.christmas.letter.sender.model.Letter;

import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterService {

  @NonNull
  private final SnsTemplate snsTemplate;

  private static final String Sns_Topic_Arn = "arn:aws:sns:us-east-1:000000000000:ChristmasLetterSns";;

  public String publishLetter(Letter letter) {

      snsTemplate.convertAndSend(Sns_Topic_Arn, letter);
      return "Message published successfully";

  }

}


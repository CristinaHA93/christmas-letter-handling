package com.christmas.letter.sender.exceptions;

import com.christmas.letter.sender.model.Letter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LetterValidator {

  public void validate(Letter letter){
    List<String> errorMessages = new ArrayList<>();
    if(letter.getLocation().getLatitude() == 0 || letter.getLocation().getLongitude() == 0){
      errorMessages.add("The location can't be empty!");
    }
    if(!errorMessages.isEmpty()){
      StringBuilder message = new StringBuilder();
      for (String item : errorMessages) {
        message.append(item).append("\n");
      }
      throw new InvalidDataException(message.toString());
    }
  }

}

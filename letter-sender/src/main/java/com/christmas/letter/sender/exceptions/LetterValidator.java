package com.christmas.letter.sender.exceptions;

import com.christmas.letter.sender.model.Letter;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class LetterValidator{
  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$");

  public void validate(Letter letter) {

    if (letter.getEmail() == null || !EMAIL_PATTERN.matcher(letter.getEmail()).matches()) {
      throw new InvalidDataException("Invalid email address provided: " + letter.getEmail());
    } else if (letter.getLocation().getLatitude() == 0 || letter.getLocation().getLongitude() == 0) {
      throw new InvalidDataException("Invalid coordinates, the coordinate should not be 0: " +
          letter.getLocation().getLatitude() + " or " + letter.getLocation().getLongitude());
    }
  }
}

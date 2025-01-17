package com.christmas.letter.processor.utils;

import com.christmas.letter.processor.dto.LetterDto;
import com.christmas.letter.processor.dto.LocationDto;
import com.christmas.letter.processor.entities.Letter;
import com.christmas.letter.processor.entities.Location;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LetterUtils {

  public static Letter getLetter(){
    Letter letter = new Letter();
    letter.setEmail("test@gmail.com");
    letter.setName("Cristina");
    letter.setWishes("Dear Santa, I wish to visit you this year.");

    Location location = new Location();
    location.setLatitude(45.760696);
    location.setLongitude(21.226788);
    letter.setLocation(location);

    return letter;
  }

  public static LetterDto getLetterDto(){
    LetterDto letter = new LetterDto();
    letter.setEmail("test@gmail.com");
    letter.setName("Cristina");
    letter.setWishes("Dear Santa, I wish to visit you this year.");

    LocationDto location = new LocationDto();
    location.setLatitude(45.760696);
    location.setLongitude(21.226788);
    letter.setLocation(location);

    return letter;
  }

}

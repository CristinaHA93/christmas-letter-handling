package com.christmas.letter.sender.service;

import com.christmas.letter.sender.exceptions.InvalidDataException;
import com.christmas.letter.sender.exceptions.LetterValidator;
import com.christmas.letter.sender.model.Letter;
import com.christmas.letter.sender.model.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import static org.hamcrest.MatcherAssert.assertThat;;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SnsServiceTest {

  @Mock
  private SnsClient snsClient;

  @InjectMocks
  private LetterService letterService;

  private ObjectMapper objectMapper;

  @Mock
  private LetterValidator letterValidator;

  @Captor
  private ArgumentCaptor<PublishRequest> publishRequestCaptor;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    objectMapper = new ObjectMapper();
    letterService = new LetterService(snsClient, letterValidator, objectMapper);
  }

  @Test
  void When_CreateLetter_Expect_LetterPublished() {

    PublishResponse mockResponse = PublishResponse.builder()
        .build();

    Mockito.when(snsClient.publish(any(PublishRequest.class))).thenReturn(mockResponse);

    String returned = letterService.publishLetter(createLetter());

    assertThat(returned, notNullValue());

  }

  @Test
  void When_CreateLetter_Expect_LetterProperlySet() {

    PublishResponse mockResponse = PublishResponse.builder()
        .build();

    when(snsClient.publish(any(PublishRequest.class))).thenReturn(mockResponse);

    String returned = letterService.publishLetter(createLetter());

    verify(snsClient, times(1)).publish(publishRequestCaptor.capture());
    String publishedMessage = publishRequestCaptor.getValue().message();

    assertEquals("Message published successfully", returned);
    assertTrue(publishedMessage.contains("\"name\":\"Cristina\""));
    assertTrue(publishedMessage.contains("\"email\":\"Santa@gmail.com\""));
    assertTrue(publishedMessage.contains("\"wishes\":\"Dear Santa, I wish to visit you this year.\""));
    assertTrue(publishedMessage.contains("\"latitude\":45.760696"));
    assertTrue(publishedMessage.contains("\"longitude\":21.226788"));
  }

  @Test
  void When_CreateLetter_With_InvalidEmail_ThrowError() {

    Letter letter = new Letter();
    letter.setEmail("invalid-email");
    Location location = new Location();
    location.setLatitude(45.760696);
    location.setLongitude(21.226788);
    letter.setLocation(location);

    Mockito.doThrow(new InvalidDataException("Invalid email address provided"))
        .when(letterValidator).validate(letter);


    InvalidDataException exception = assertThrows(
        InvalidDataException.class,
        () -> letterService.publishLetter(letter)
    );

    assertEquals("Invalid email address provided", exception.getMessage());

    verify(snsClient, never()).publish(any(PublishRequest.class));

  }

  @Test
  void When_CreateLetter_With_InvalidCoordinate_Expect_ThrowError() {
    // Arrange
    Letter letter = new Letter();
    letter.setEmail("CristinaTest@example.com");
    letter.setName("Cristina");
    letter.setWishes("Dear Santa, I wish to visit you this year.");

    Location location = new Location();
    location.setLatitude(0);
    location.setLongitude(-74.0060);
    letter.setLocation(location);

    Mockito.doThrow(new InvalidDataException("Invalid coordinates, the coordinate should not be 0:"))
        .when(letterValidator).validate(letter);

    InvalidDataException exception = assertThrows(
        InvalidDataException.class,
        () -> letterService.publishLetter(letter)
    );

    assertEquals("Invalid coordinates, the coordinate should not be 0:", exception.getMessage());

    verify(snsClient, never()).publish(any(PublishRequest.class));

  }

  @Test
  void When_CreateLetter_Expect_ThrowError(){

    Mockito.doThrow(new InvalidDataException("")).when(this.letterValidator).validate(any());

    Letter letter = new Letter();

    assertThrows(InvalidDataException.class, () -> this.letterService.publishLetter(letter));

  }


  private Letter createLetter(){
    Letter letter = new Letter();
    letter.setEmail("Santa@gmail.com");
    letter.setName("Cristina");
    letter.setWishes("Dear Santa, I wish to visit you this year.");

    Location location = new Location();
    location.setLatitude(45.760696);
    location.setLongitude(21.226788);
    letter.setLocation(location);

    return letter;
  }

}



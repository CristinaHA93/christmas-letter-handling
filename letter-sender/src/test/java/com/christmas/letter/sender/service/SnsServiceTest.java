package com.christmas.letter.sender.service;

import com.christmas.letter.sender.exceptions.InvalidDataException;
import com.christmas.letter.sender.model.Letter;
import com.christmas.letter.sender.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import static org.hamcrest.MatcherAssert.assertThat;;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class SnsServiceTest {

  @Mock
  private SnsClient snsClient;

  @InjectMocks
  private SnsService snsService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void When_CreateLetter_Expect_LetterPublished() {

    PublishResponse mockResponse = PublishResponse.builder()
        .build();

    when(snsClient.publish(any(PublishRequest.class))).thenReturn(mockResponse);

    String returned = snsService.publishLetter(createLetter());

    assertThat(returned, notNullValue());

  }

  @Test
  void When_CreateLetter_Expect_failure() {

    InvalidDataException exception = assertThrows(
        InvalidDataException.class,
        () -> snsService.publishLetter(createInvalidLetter())
    );

    assertEquals("Invalid email address provided: test", exception.getMessage());

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

  private Letter createInvalidLetter(){
    Letter letter = new Letter();
    letter.setEmail("test");
    letter.setName("Cristina");
    letter.setWishes("Dear Santa, I wish to visit you this year.");

    Location location = new Location();
    location.setLatitude(45.760696);
    location.setLongitude(21.226788);
    letter.setLocation(location);

    return letter;
  }

}



package com.christmas.letter.sender.service;

import com.christmas.letter.sender.model.Letter;
import com.christmas.letter.sender.model.Location;
import io.awspring.cloud.sns.core.SnsTemplate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import static org.hamcrest.MatcherAssert.assertThat;;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LetterServiceTest {

  private static final String SNS_TOPIC_ARN = "arn:aws:sns:us-east-1:000000000000:ChristmasLetterSns";

  @Mock
  private SnsTemplate snsTemplate;

  @InjectMocks
  private LetterService letterService;

  @Autowired
  private Validator validator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    this.validator = factory.getValidator();
  }

  @Test
  void When_CreateLetter_Expect_LetterPublished() {

    String returned = letterService.publishLetter(createLetter());

    assertThat(returned, notNullValue());

  }

  @Test
  void When_CreateLetter_Expect_LetterProperlySet() {

    snsTemplate = mock(SnsTemplate.class);
    letterService = new LetterService(snsTemplate);

    letterService.publishLetter(createLetter());

    ArgumentCaptor<Letter> letterCaptor = ArgumentCaptor.forClass(Letter.class);

    verify(snsTemplate, times(1)).convertAndSend(eq(SNS_TOPIC_ARN), letterCaptor.capture());

    Letter capturedLetter = letterCaptor.getValue();
    assertEquals("Santa@gmail.com", capturedLetter.getEmail());
    assertEquals("Cristina", capturedLetter.getName());
    assertEquals("Dear Santa, I wish to visit you this year.", capturedLetter.getWishes());
    assertEquals(45.760696, capturedLetter.getLocation().getLatitude(), 0.0001);
    assertEquals(21.226788, capturedLetter.getLocation().getLongitude(), 0.0001);

  }

  @Test
  void When_CreateLetter_With_InvalidEmail_ThrowError() {

    Letter letter = new Letter();
    letter.setEmail("invalid-email");
    letter.setName("Cristina");
    letter.setWishes("Dear Santa, I wish to visit you this year.");

    Location location = new Location();
    location.setLatitude(45.760696);
    location.setLongitude(-74.0060);
    letter.setLocation(location);

    Set<ConstraintViolation<Letter>> violations = validator.validate(letter);
    assertFalse(violations.isEmpty());

    ConstraintViolation<Letter> violation = violations.iterator().next();
    assertEquals("The email is invalid!", violation.getMessage());

    verify(snsTemplate, never()).convertAndSend(eq(SNS_TOPIC_ARN),any(PublishRequest.class));

  }

  @Test
  void When_CreateLetter_With_InvalidCoordinate_Expect_ThrowError() {

    Letter letter = new Letter();
    letter.setEmail("test@gmail.com");
    letter.setName("Cristina");
    letter.setWishes("Dear Santa, I wish to visit you this year.");

    Location location = new Location();
    location.setLatitude(91.0);
    location.setLongitude(-74.0060);
    letter.setLocation(location);

    Set<ConstraintViolation<Letter>> violations = validator.validate(letter);
    assertFalse(violations.isEmpty());

    ConstraintViolation<Letter> violation = violations.iterator().next();
    assertEquals("Latitude must be between -90 and 90.", violation.getMessage());

    verify(snsTemplate, never()).convertAndSend(eq(SNS_TOPIC_ARN),any(PublishRequest.class));

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



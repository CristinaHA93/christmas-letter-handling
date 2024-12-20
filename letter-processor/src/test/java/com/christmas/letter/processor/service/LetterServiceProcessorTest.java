package com.christmas.letter.processor.service;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.christmas.letter.processor.dto.LetterDto;
import com.christmas.letter.processor.entities.Letter;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import com.christmas.letter.processor.mapper.LetterMapper;
import com.christmas.letter.processor.repository.LetterRepository;
import java.util.Optional;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LetterServiceProcessorTest {

  @Mock
  private LetterRepository letterRepository;

  @Mock
  private LetterMapper letterMapper;

  @InjectMocks
  private LetterServiceProcessor letterService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void When_GetLetterByEmail_Expect_Success() {

    String email = "cristinaTest@gmail.com";


    when(letterRepository.findById(email)).thenReturn(Optional.of(new Letter()));

    // Act
    LetterDto result = letterService.getLetterByEmail(email);

    // Assert
    assertNotNull(result);
    verify(letterRepository, times(1)).findById(email);

  }


    // TO DO error tests and getAllLetter tests



}

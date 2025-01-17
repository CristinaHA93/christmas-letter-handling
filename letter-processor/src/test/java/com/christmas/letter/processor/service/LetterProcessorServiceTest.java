package com.christmas.letter.processor.service;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.christmas.letter.processor.dto.LetterDto;
import com.christmas.letter.processor.entities.Letter;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.christmas.letter.processor.exception.NotFoundException;
import com.christmas.letter.processor.mapper.LetterMapper;
import com.christmas.letter.processor.repository.LetterRepository;
import com.christmas.letter.processor.utils.LetterUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class LetterProcessorServiceTest {

  @Mock
  private LetterRepository letterRepository;

  @Mock
  private LetterMapper letterMapper;

  @InjectMocks
  private LetterProcessorService letterService;


  @Test
  void When_GetLetterByEmail_Expect_Success() {

    String email = "test@gmail.com";
    Letter savedLetter = LetterUtils.getLetter();
    LetterDto message = LetterUtils.getLetterDto();
    when(letterRepository.findById(email)).thenReturn(Optional.of(savedLetter));
    when(letterMapper.toLetterDto(savedLetter)).thenReturn(message);

    // Act
    LetterDto result = letterService.getLetterByEmail(email);

    // Assert
    assertThat(result).isEqualTo(message);

  }

  @Test
  void When_GetLetterByEmail_Expect_NotFound() {

    String email = "test@example.com";
    when(letterRepository.findById(email)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> letterService.getLetterByEmail(email));
    verify(letterRepository).findById(email);
    verify(letterMapper, never()).toLetterDto(any());

  }

  @Test
  void When_GetAllLetters_Expect_returnLetters() {

    Pageable pageable = PageRequest.of(0, 10);
    Letter letter1 = new Letter();
    Letter letter2 = new Letter();
    List<Letter> letters = Arrays.asList(letter1, letter2);
    Page<Letter> letterPage = new PageImpl<>(letters);
    LetterDto letterDto1 = new LetterDto();
    LetterDto letterDto2 = new LetterDto();

    when(letterRepository.findAll(pageable)).thenReturn(letterPage);
    when(letterMapper.toLetterDto(letter1)).thenReturn(letterDto1);
    when(letterMapper.toLetterDto(letter2)).thenReturn(letterDto2);

    Page<LetterDto> result = letterService.getPaginatedLetters(pageable);

    assertEquals(2, result.getTotalElements());
    verify(letterRepository).findAll(pageable);

  }

}



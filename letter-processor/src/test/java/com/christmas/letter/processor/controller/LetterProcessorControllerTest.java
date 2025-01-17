package com.christmas.letter.processor.controller;


import com.christmas.letter.processor.entities.Letter;
import com.christmas.letter.processor.repository.LetterRepository;
import com.christmas.letter.processor.utils.ContainerTest;
import com.christmas.letter.processor.utils.LetterUtils;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
@TestPropertySource("classpath:testProcessor.properties")
class LetterProcessorControllerTest extends ContainerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private LetterRepository letterRepository;

  private static final String LETTER_API_PATH = "/api/v1/letters";

  @Test
  void When_GiveUnsavedEmail_ThenThrowNotFoundException() throws Exception {
    // Arrange
    String email = "test@example.com";

    // Act && Assert
    mockMvc.perform(get(String.format("%s/{email}", LETTER_API_PATH), email))
        .andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.content().string(String.format("Email %s not found", email)));
  }

  @Test
  void When_GiveInvalidEmail_ThenThrowValidationException() throws Exception {

    String email = "invalid_email";

    mockMvc.perform(get(String.format("%s/{email}", LETTER_API_PATH), email))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Validation failure"))
        .andExpect(jsonPath("$.errors[0].email").value("Invalid email"));
  }

  @ParameterizedTest
  @MethodSource("generateLetters")
  void When_GivePage_thenReturnPageLetters(Pageable pageable, List<Letter> letters) throws Exception {

    letterRepository.saveAll(letters);

    mockMvc.perform(get(LETTER_API_PATH)
        .param("page", String.valueOf(pageable.getPageNumber()))
        .param("size", String.valueOf(pageable.getPageSize())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.totalElements").value(letters.size()))
        .andExpect(jsonPath("$.pageable.pageSize").value(pageable.getPageSize()))
        .andExpect(jsonPath("$.pageable.pageNumber").value(pageable.getPageNumber()))
        .andExpect(jsonPath("$.pageable.offset").value(pageable.getOffset()));
  }


  private static Stream<Arguments> generateLetters() {
    return Stream.of(
        Arguments.of(PageRequest.of(0, 8), getNewLetters(1)),
        Arguments.of(PageRequest.of(1, 2), getNewLetters(3))
    );
  }

  private static List<Letter> getNewLetters(int count) {
    return IntStream.range(0, count).mapToObj(el -> LetterUtils.getLetter()).toList();
  }

}

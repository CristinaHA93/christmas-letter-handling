package com.christmas.letter.sender.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.christmas.letter.sender.model.Letter;
import com.christmas.letter.sender.model.Location;
import com.christmas.letter.sender.service.LetterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LetterController.class)
class LetterControllerTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LetterService letterService;

  private static final String PATH = "/api/letters";

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
   void When_CreateLetter_Expect_LetterSend() throws Exception {

    mockMvc.perform(post(PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createLetter())))
        .andExpect(status().isCreated());

  }

 @Test
  void When_CreateLetter_Expect_BadRequest() throws Exception {

    String invalidTestMessage = objectMapper.writeValueAsString(createInvalidLetter());

    mockMvc.perform(post(PATH)
       .contentType(MediaType.APPLICATION_JSON)
       .content(invalidTestMessage))
       .andExpect(status().isBadRequest());

  }

  private Letter createLetter(){
    Letter letter = new Letter();
    letter.setEmail("cristina@gmail.com");
    letter.setName("Cristina");
    letter.setWishes("Dear Santa, I wish to visit you this year.");

    Location location = new Location();
    location.setLatitude(45.760696);
    location.setLongitude(21.226788);
    letter.setLocation(location);

    return letter;
  }

  private static Letter createInvalidLetter(){
    Letter letter = new Letter();
    letter.setEmail("invalid-email");
    letter.setName("Cristina");
    letter.setWishes("Dear Santa, I wish to visit you this year.");

    Location location = new Location();
    location.setLatitude(45.760696);
    location.setLongitude(21.226788);
    letter.setLocation(location);

    return letter;
  }

}

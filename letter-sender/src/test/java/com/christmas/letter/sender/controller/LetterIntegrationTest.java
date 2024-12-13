package com.christmas.letter.sender.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.christmas.letter.sender.model.Letter;
import com.christmas.letter.sender.model.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
class LetterIntegrationTest extends ContainerTest{

  @Autowired
  private MockMvc mockMvc;

  private static final String PATH = "/api/letters";
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void When_PublishMessage_Expect_MessageReceived() throws Exception {

    mockMvc.perform(post(PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createLetter())))
        .andExpect(status().isCreated())
        .andExpect(content().string("Message published successfully"));

  }

  @Test
  void  When_PublishMessage_Expect_ThenValidationFails() throws Exception {

    mockMvc.perform(post(PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createInvalidLetter())))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("The validation has failed!"))
        .andExpect(jsonPath("$.reasons", hasItem("The email is invalid!")))
        .andExpect(jsonPath("$.reasons", hasItem("Name required")))
        .andExpect(jsonPath("$.reasons", hasItem("Please add your wishes!")))
        .andExpect(jsonPath("$.reasons", hasItem("Longitude must be between -90 and 90.")));

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
    letter.setName("");
    letter.setWishes("");

    Location location = new Location();
    location.setLatitude(90.0);
    location.setLongitude(120.226788);
    letter.setLocation(location);

    return letter;
  }

}

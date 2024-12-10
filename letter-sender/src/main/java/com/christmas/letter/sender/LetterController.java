package com.christmas.letter.sender;

import com.christmas.letter.sender.model.Letter;
import com.christmas.letter.sender.service.LetterService;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/letters")
@RequiredArgsConstructor
public class LetterController {

  @NonNull
  private final LetterService letterService;

  @PostMapping
  public ResponseEntity<String> create(@Valid @RequestBody Letter letter) {

      String result = letterService.publishLetter(letter);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

}

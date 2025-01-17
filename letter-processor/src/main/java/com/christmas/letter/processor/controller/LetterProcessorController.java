package com.christmas.letter.processor.controller;

import com.christmas.letter.processor.dto.LetterDto;
import com.christmas.letter.processor.service.LetterProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LetterProcessorController {

  private final LetterProcessorService service;

  @GetMapping("/letters/{email}")
  public ResponseEntity<LetterDto> getLetterByEmail(@PathVariable String email) {

    LetterDto letter = service.getLetterByEmail(email);
    return new ResponseEntity<>(letter, HttpStatus.OK);

  }

  @GetMapping("/letters")
  public Page<LetterDto> getLetters(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);
    return service.getPaginatedLetters(pageable);

  }

}

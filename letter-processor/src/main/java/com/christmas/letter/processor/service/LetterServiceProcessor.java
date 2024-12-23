package com.christmas.letter.processor.service;


import com.christmas.letter.processor.dto.LetterDto;
import com.christmas.letter.processor.entities.Letter;
import com.christmas.letter.processor.exception.NotFoundException;
import com.christmas.letter.processor.mapper.LetterMapper;
import com.christmas.letter.processor.repository.LetterRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterServiceProcessor {

  @NonNull
  private final LetterRepository repository;

  @NonNull
  private final LetterMapper mapper;

  public LetterDto getLetterByEmail(String email) {
    Letter letter = repository.findById(email).orElseThrow(() -> new NotFoundException(email));
    return  mapper.toLetterDto(letter);
  }

  public Page<LetterDto> getPaginatedLetters(Pageable pageable) {
    return repository.findAll(pageable).map(mapper::toLetterDto);
  }

}

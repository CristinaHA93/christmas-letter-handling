package com.christmas.letter.processor.service;


import com.christmas.letter.processor.dto.LetterDto;
import com.christmas.letter.processor.entities.Letter;
import com.christmas.letter.processor.exception.NotFoundException;
import com.christmas.letter.processor.mapper.LetterMapper;
import com.christmas.letter.processor.repository.LetterRepository;
import io.awspring.cloud.sqs.annotation.SqsListener;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterServiceProcessor {

  @NonNull
  private final LetterRepository repository;

  @NonNull
  private final LetterMapper mapper;

  //TO DO... test better this
  @SqsListener(value="${aws.sqs.url}")
  public void listen(@Valid @Payload LetterDto letterDto) {
    repository.save(mapper.toLetter(letterDto));
  }

  public LetterDto getLetterByEmail(String email) {
    Letter letter = repository.findById(email).orElseThrow(() -> new NotFoundException(email));
    return  mapper.toLetterDto(letter);
  }

  public Page<LetterDto> getPaginatedLetters(Pageable pageable) {
    return repository.findAll(pageable).map(mapper::toLetterDto);
  }

}

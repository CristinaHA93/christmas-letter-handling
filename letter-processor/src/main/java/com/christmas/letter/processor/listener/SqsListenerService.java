package com.christmas.letter.processor.listener;

import com.christmas.letter.processor.dto.LetterDto;
import com.christmas.letter.processor.mapper.LetterMapper;
import com.christmas.letter.processor.repository.LetterRepository;
import io.awspring.cloud.sqs.annotation.SqsListener;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqsListenerService {

  private final LetterRepository repository;

  private final LetterMapper mapper;

  @SqsListener("${aws.sqs.url}")
  public void listen(@Valid @Payload LetterDto letterDto) {
    repository.save(mapper.toLetter(letterDto));
  }

}

package com.christmas.letter.processor.config;

import com.christmas.letter.processor.mapper.LetterMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

  @Bean
  public LetterMapper letterMapper() {
    return Mappers.getMapper(LetterMapper.class);
  }

}

package com.christmas.letter.processor.mapper;

import com.christmas.letter.processor.dto.LetterDto;
import com.christmas.letter.processor.entities.Letter;
import org.mapstruct.Mapper;

@Mapper
public interface LetterMapper {

  Letter toLetter(LetterDto letterDto);

  LetterDto toLetterDto(Letter letterEntity);

}

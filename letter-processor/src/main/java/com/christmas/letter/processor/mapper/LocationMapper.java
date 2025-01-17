package com.christmas.letter.processor.mapper;

import com.christmas.letter.processor.dto.LocationDto;
import com.christmas.letter.processor.entities.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

  LocationDto toLocationDto(Location locationEntity);
}

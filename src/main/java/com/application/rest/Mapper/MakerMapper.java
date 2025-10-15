package com.application.rest.Mapper;


import com.application.rest.Controllers.DTO.MakerDTO;
import com.application.rest.Entities.Maker;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MakerMapper {
    MakerMapper instance = Mappers.getMapper(MakerMapper.class);

    MakerDTO toDTO (Maker maker);
    Maker toEntity (MakerDTO makerDTO);
}

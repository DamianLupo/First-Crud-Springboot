package com.application.rest.Mapper;


import com.application.rest.Controllers.DTO.ProductDTO;
import com.application.rest.Entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper instance = Mappers.getMapper(ProductMapper.class);
    ProductDTO toDTO(Product product);
    Product toEntity (ProductDTO productDTO);
    @Mapping(target = "id", ignore = true)
    void updateProductFromDTO(ProductDTO productDTO, @MappingTarget Product product); //Actualizo el objeto ignorando si se intenta sobreescribir el id
}

package com.application.rest.Controllers.DTO;

import com.application.rest.Entities.Maker;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacio")
    private String name;

    @NotNull(message = "El precio del producto es obligatorio")
    @Positive(message = "El precio debe ser mayor que cero")
    private BigDecimal price; //Similar a un double pero mas preciso para trabajar con plata

    @NotNull(message = "El producto debe tener un fabricante asociado")
    private Maker maker;
}

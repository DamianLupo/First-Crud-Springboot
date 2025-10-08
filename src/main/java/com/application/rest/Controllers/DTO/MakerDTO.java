package com.application.rest.Controllers.DTO;

import com.application.rest.Entities.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MakerDTO {

    private Long id;
    @NotBlank(message = "El nombre del fabricante no puede estar vacio")
    private String name;
    private List<Product> products = new ArrayList<>();
}

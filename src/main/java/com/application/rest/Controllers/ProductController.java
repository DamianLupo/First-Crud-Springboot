package com.application.rest.Controllers;


import com.application.rest.Controllers.DTO.ProductDTO;
import com.application.rest.Entities.Product;
import com.application.rest.Exceptions.BadRequestException;
import com.application.rest.Exceptions.ResourceNotFoundException;
import com.application.rest.Service.iProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final iProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id)
    {
        Product product= productService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Producto con id "+ id + "no encontrado"));
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .maker(product.getMaker())
                .build();

        return ResponseEntity.ok(productDTO);
    }
    @GetMapping("/")
    public ResponseEntity<?> findAll()
    {
        List<ProductDTO> products = productService.findAll().stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build())
                .toList();
        return ResponseEntity.ok(products);
    }
    @PostMapping("/")
    public ResponseEntity<Void> save(@Valid @RequestBody ProductDTO productDTO)
    {

        productService.save(Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .maker(productDTO.getMaker()).build());
        return ResponseEntity.created(URI.create("/api/product/save")).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@Valid @PathVariable Long id, @RequestBody ProductDTO productDTO)
    {
        Product product= productService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Producto con id "+id+" no encontrado"));
        product.setName(productDTO.getName());
        product.setMaker(product.getMaker());
        product.setPrice(product.getPrice());
        productService.save(product);



        return ResponseEntity.ok("Registro actualizado");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id)
    {
        if(id==null)
        {

            throw new BadRequestException("El id no puede ser nulo");
        }
        productService.deleteById(id);
        return ResponseEntity.ok("Registro Eliminado");
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> findProductByPriceBetween(@RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice)
    {
        if(minPrice==null || maxPrice== null || minPrice.compareTo(maxPrice)>=0)
        {
            throw new BadRequestException("El rango de precios es invalido");
        }
        List<ProductDTO> products = productService.findByPriceInRange(minPrice, maxPrice)
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build()
                )
                .toList();
        if(products.isEmpty())
        {
            throw new ResourceNotFoundException("No se encontraron productos en el rango especificado");
        }
        return ResponseEntity.ok(products);
    }
}

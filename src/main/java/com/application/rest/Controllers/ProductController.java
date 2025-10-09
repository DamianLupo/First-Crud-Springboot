package com.application.rest.Controllers;


import com.application.rest.Controllers.DTO.ProductDTO;
import com.application.rest.Entities.Product;
import com.application.rest.Exceptions.BadRequestException;
import com.application.rest.Exceptions.ResourceNotFoundException;
import com.application.rest.Service.iProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private static final int maxPageSize = 50;
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
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size)
    {
        if(size>maxPageSize)
        {
            size=maxPageSize;
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<ProductDTO> products = productService.findAll(pageable)
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build());
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
    public ResponseEntity<Page<ProductDTO>> findProductByPriceBetween(@RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice
    ,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5")int size)
    {
        if(size>maxPageSize)
        {
            size=maxPageSize;
        }
        if(minPrice==null || maxPrice== null || minPrice.compareTo(maxPrice)>=0)
        {
            throw new BadRequestException("El rango de precios es invalido");
        }
        Pageable pageable= PageRequest.of(page,size);
        Page<ProductDTO> products = productService.findByPriceInRange(minPrice, maxPrice, pageable)
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build()
                );

        if(products.isEmpty())
        {
            throw new ResourceNotFoundException("No se encontraron productos en el rango especificado");
        }
        return ResponseEntity.ok(products);
    }
}

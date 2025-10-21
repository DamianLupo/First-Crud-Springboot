package com.application.rest.Controllers;


import com.application.rest.Controllers.DTO.ProductDTO;
import com.application.rest.Entities.Product;
import com.application.rest.Exceptions.BadRequestException;
import com.application.rest.Exceptions.ResourceNotFoundException;
import com.application.rest.Mapper.ProductMapper;
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
    private final ProductMapper productMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id)
    {
        Product product= productService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Producto con id "+ id + "no encontrado"));
        ProductDTO productDTO = productMapper.toDTO(product);

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
                .map(productMapper::toDTO);
        return ResponseEntity.ok(products);
    }
    @PostMapping("/")
    public ResponseEntity<Void> save(@Valid @RequestBody ProductDTO productDTO)
    {

        productService.save(productMapper.toEntity(productDTO));
        return ResponseEntity.created(URI.create("/api/product/")).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@Valid @PathVariable Long id, @RequestBody ProductDTO productDTO)
    {
        Product product= productService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Producto con id "+id+" no encontrado"));
        productMapper.updateProductFromDTO(productDTO,product);
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
                .map(productMapper::toDTO
                );

        if(products.isEmpty())
        {
            throw new ResourceNotFoundException("No se encontraron productos en el rango especificado");
        }
        return ResponseEntity.ok(products);
    }
}

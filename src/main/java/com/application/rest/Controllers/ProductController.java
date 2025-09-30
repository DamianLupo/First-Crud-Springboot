package com.application.rest.Controllers;


import com.application.rest.Controllers.DTO.ProductDTO;
import com.application.rest.Entities.Product;
import com.application.rest.Service.iProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product")
public class ProductController {
    @Autowired
    private iProductService productService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id)
    {
        Optional<Product> productOptional = productService.findById(id);
        if(productOptional.isPresent())
        {
            Product product = productOptional.get();
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .maker(product.getMaker())
                    .build();
            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/findAll")
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
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO)
    {
        if(productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getMaker() == null)
        {
            return ResponseEntity.badRequest().build();
        }
        productService.save(Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .maker(productDTO.getMaker()).build());
        return ResponseEntity.created(URI.create("/api/product/save")).build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody ProductDTO productDTO)
    {
        Optional<Product> productOptional = productService.findById(id);
        if(productOptional.isPresent())
        {
            Product product = productOptional.get();
            product.setName(productDTO.getName());
            product.setMaker(product.getMaker());
            product.setPrice(product.getPrice());
            productService.save(product);
            return ResponseEntity.ok("Registro actualizado");

        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
        if(id!=null)
        {
            productService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}

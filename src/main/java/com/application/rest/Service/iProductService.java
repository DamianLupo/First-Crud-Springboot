package com.application.rest.Service;

import com.application.rest.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.math.BigDecimal;

import java.util.Optional;

public interface iProductService {
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(Long id);
    Page<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    void save(Product product);
    void deleteById(Long id);
}

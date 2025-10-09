package com.application.rest.Persistence;

import com.application.rest.Entities.Product;

import java.math.BigDecimal;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface iProductDAO {
    Page<Product> findAll(Pageable pagleable);
    Optional<Product> findById(Long id);
    Page<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    void save(Product product);
    void deleteById(Long id);
}

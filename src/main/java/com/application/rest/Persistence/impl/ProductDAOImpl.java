package com.application.rest.Persistence.impl;

import com.application.rest.Entities.Product;
import com.application.rest.Persistence.iProductDAO;
import com.application.rest.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Component
public class ProductDAOImpl implements iProductDAO {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable) ;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findProductByPriceBetween(minPrice,maxPrice,pageable);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}

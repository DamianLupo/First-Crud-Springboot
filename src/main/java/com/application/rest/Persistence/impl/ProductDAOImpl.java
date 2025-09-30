package com.application.rest.Persistence.impl;

import com.application.rest.Entities.Product;
import com.application.rest.Persistence.iProductDAO;
import com.application.rest.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class ProductDAOImpl implements iProductDAO {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return (List<Product>)productRepository.findAll() ;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findProductByPriceBetween(minPrice,maxPrice);
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

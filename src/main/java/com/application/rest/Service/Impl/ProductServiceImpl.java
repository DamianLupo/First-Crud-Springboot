package com.application.rest.Service.Impl;

import com.application.rest.Entities.Product;
import com.application.rest.Persistence.iProductDAO;
import com.application.rest.Service.iProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;

import java.util.Optional;

@Service
public class ProductServiceImpl implements iProductService {

    @Autowired
    private iProductDAO productDAO;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productDAO.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productDAO.findById(id);
    }

    @Override
    public Page<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice,Pageable pageable) {
        return productDAO.findByPriceInRange(minPrice,maxPrice, pageable);
    }

    @Override
    public void save(Product product) {
        productDAO.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productDAO.deleteById(id);
    }
}

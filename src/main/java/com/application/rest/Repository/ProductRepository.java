package com.application.rest.Repository;

import com.application.rest.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    Page<Product> findProductByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    //Esto es con querymethod
    Page<Product> findProductByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

}

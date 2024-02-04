package com.maradamark99.thesis.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.value = :categoryName")
    Page<Product> findAllByCategoryName(Pageable pageable, @Param("categoryName") String categoryName);
}
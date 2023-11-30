package com.maradamark99.thesis.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductDTO> getAll(Pageable pageable);
    Long create(ProductDTO productDTO);
}

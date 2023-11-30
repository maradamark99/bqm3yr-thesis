package com.maradamark99.thesis.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
public interface ProductService {
    Page<ProductDTO> getAll(Pageable pageable);
    Long create(ProductDTO productDTO);
    void uploadMedia(long id, MultipartFile file) throws IOException;
}

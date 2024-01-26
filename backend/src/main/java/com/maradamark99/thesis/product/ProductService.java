package com.maradamark99.thesis.product;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    Page<ProductDTO> getAll(Pageable pageable);

    Long create(ProductDTO productDTO);

    void deleteById(long id);

    void uploadProductMedia(long id, MultipartFile file) throws IOException;

    void uploadThumbnailImage(long id, MultipartFile file) throws IOException;
}

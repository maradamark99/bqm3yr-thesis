package com.maradamark99.thesis.product;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    Page<ProductDTO> getAll(Pageable pageable);

    Long create(ProductDTO productDTO);

    void uploadProductMedia(long id, MultipartFile file, boolean isThumbnail) throws IOException;

    void deleteById(long id);

}

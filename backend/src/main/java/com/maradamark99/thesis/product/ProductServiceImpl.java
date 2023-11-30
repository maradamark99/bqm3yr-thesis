package com.maradamark99.thesis.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public Page<ProductDTO> getAll(Pageable pageable) {
        return productRepository
                .findAll(pageable)
                .map(productMapper::entityToDto);
    }

    @Override
    public Long create(ProductDTO productDTO) {
        // TODO: check for duplicates
        return productRepository
                .save(productMapper.dtoToEntity(productDTO))
                .getId();
    }
}

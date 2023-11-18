package com.maradamark99.thesis.product;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product dtoToEntity(ProductDTO dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .condition(dto.getCondition())
                .currentPrice(dto.getCurrentPrice())
                .build();
    }

    public ProductDTO entityToDto(Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .condition(entity.getCondition())
                .currentPrice(entity.getCurrentPrice())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

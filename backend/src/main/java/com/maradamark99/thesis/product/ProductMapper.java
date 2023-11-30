package com.maradamark99.thesis.product;

import com.maradamark99.thesis.category.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public Product dtoToEntity(ProductDTO dto, List<Category> categories) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .condition(dto.getCondition())
                .categories(categories)
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

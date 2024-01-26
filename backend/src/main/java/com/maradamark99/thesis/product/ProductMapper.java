package com.maradamark99.thesis.product;

import com.maradamark99.thesis.category.Category;
import com.maradamark99.thesis.storage.StorageConfig;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final StorageConfig storageConfig;

    public Product dtoToEntity(ProductDTO dto, Set<Category> categories) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .condition(dto.getCondition())
                .categories(categories)
                .currentPrice(dto.getCurrentPrice())
                .build();
    }

    public ProductDTO entityToDto(Product entity) {
        var thumbnailImg = entity.getThumbnailImage();
        String thumbnailUrl = null;
        if (thumbnailImg != null) {
            thumbnailUrl = storageConfig.getEndpoint() + thumbnailImg.getPath();
        }
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .condition(entity.getCondition())
                .currentPrice(entity.getCurrentPrice())
                .thumbnailUrl(thumbnailUrl)
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

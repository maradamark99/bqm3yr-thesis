package com.maradamark99.thesis.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    CategoryDTO entityToDto(Category category) {
        var parent = category.getParent();
        return CategoryDTO.builder()
                .id(category.getId())
                .value(category.getValue())
                .isLeaf(category.isLeaf())
                .parentId(parent != null ? parent.getId() : null)
                .build();
    }

}

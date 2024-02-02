package com.maradamark99.thesis.category;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAll(Long parentId);

    CategoryDTO getById(long id);

    void deleteById(long id);

    long update(CategoryDTO categoryDTO, Long id);

    long create(CategoryDTO categoryDTO);

}

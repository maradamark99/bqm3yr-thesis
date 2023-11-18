package com.maradamark99.thesis.category;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAll();
    CategoryDTO getById(long id);
    List<CategoryDTO> getChildrenByParentId(long id);
    void deleteById(long id);
    long update(CategoryDTO categoryDTO, Long id);
    long create(CategoryDTO categoryDTO);

}

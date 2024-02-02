package com.maradamark99.thesis.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByValue(String value);

    List<Category> findByParentIdIsNull();

    Set<Category> findByIdIn(List<Long> ids);

}

package com.maradamark99.thesis.category;

import com.maradamark99.thesis.exception.ResourceAlreadyExistsException;
import com.maradamark99.thesis.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final Function<Long, String> CATEGORY_NOT_FOUND_MESSAGE = (id) -> "Category with id: %d does not exist"
            .formatted(id);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAll(Long parentId) {
        if (parentId != null) {
            var parent = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE.apply(parentId)));
            return parent
                    .getChildren()
                    .stream()
                    .map(categoryMapper::entityToDto)
                    .toList();
        }
        return categoryRepository.findByParentIdIsNull()
                .stream()
                .map(categoryMapper::entityToDto)
                .toList();
    }

    @Override
    public CategoryDTO getById(long id) {
        var category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE.apply(id));
        }
        return categoryMapper.entityToDto(category.get());
    }

    @Override
    public void deleteById(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE.apply(id));
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public long update(CategoryDTO categoryDTO, Long id) {
        var category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE.apply(id));
        }
        var toUpdate = category.get();
        toUpdate.setValue(categoryDTO.getValue());
        if (categoryDTO.getParentId() != null) {
            var parent = categoryRepository.findById(categoryDTO.getParentId());
            if (categoryDTO.getParentId() != null && parent.isEmpty()) {
                throw new ResourceNotFoundException(
                        CATEGORY_NOT_FOUND_MESSAGE.apply(categoryDTO.getParentId()));
            }
            toUpdate.setParent(parent.get());
        }
        return categoryRepository.save(toUpdate).getId();
    }

    @Override
    public long create(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByValue(categoryDTO.getValue())) {
            throw new ResourceAlreadyExistsException(
                    "Category with value: %s already exists".formatted(categoryDTO.getValue()));
        }
        Optional<Category> parentOptional = Optional.empty();
        if (categoryDTO.getParentId() != null) {
            parentOptional = categoryRepository.findById(categoryDTO.getParentId());
            if (parentOptional.isEmpty()) {
                throw new ResourceNotFoundException(
                        CATEGORY_NOT_FOUND_MESSAGE.apply(categoryDTO.getParentId()));
            }
        }
        var toSave = Category.builder()
                .value(categoryDTO.getValue())
                .parent(parentOptional.isPresent() ? parentOptional.get() : null)
                .build();
        return categoryRepository.save(toSave).getId();
    }
}

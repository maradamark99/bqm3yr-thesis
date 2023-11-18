package com.maradamark99.thesis.category;

import com.maradamark99.thesis.exception.ResourceAlreadyExistsException;
import com.maradamark99.thesis.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
// TODO: refactor: DRY, check for cycles and max depth maybe
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::entityToDto)
                .toList();
    }

    @Override
    public List<CategoryDTO> getTopLevel() {
        return categoryRepository.findByParentIdIsNull()
                .stream()
                .map(categoryMapper::entityToDto)
                .toList();
    }

    @Override
    public CategoryDTO getById(long id) {
        var category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category with id: %d does not exist".formatted(id));
        }
        return categoryMapper.entityToDto(category.get());
    }

    @Override
    public List<CategoryDTO> getChildrenByParentId(long id) {
        var parent = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Category with id: %d does not exist".formatted(id));
                });
        return parent.getChildren()
                .stream()
                .map(categoryMapper::entityToDto)
                .toList();
    }

    @Override
    public void deleteById(long id) {
        if(!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id: %d does not exist".formatted(id));
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public long update(CategoryDTO categoryDTO, Long id) {
        var category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category with id: %d does not exist".formatted(id));
        }
        var parent = categoryRepository.findById(categoryDTO.getParentId());
        if (categoryDTO.getParentId() != null && parent.isEmpty()) {
            throw new ResourceNotFoundException("Parent category with id: %d does not exist".formatted(categoryDTO.getParentId()));
        }
        var toUpdate = category.get();
        toUpdate.setValue(categoryDTO.getValue());
        toUpdate.setParent(parent.get());
        return categoryRepository.save(toUpdate).getId();
    }

    @Override
    public long create(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByValue(categoryDTO.getValue())) {
            throw new ResourceAlreadyExistsException("Category with value: %s already exists".formatted(categoryDTO.getValue()));
        }
        Optional<Category> parentOptional = Optional.empty();
        if (categoryDTO.getParentId() != null) {
            parentOptional = categoryRepository.findById(categoryDTO.getParentId());
            if (parentOptional.isEmpty()) {
                throw new ResourceNotFoundException("Parent category with id: %d does not exist".formatted(categoryDTO.getParentId()));
            }
        }
        var toSave = Category.builder()
                .value(categoryDTO.getValue())
                .parent(parentOptional.isPresent() ? parentOptional.get() : null)
                .build();
        return categoryRepository.save(toSave).getId();
    }
}

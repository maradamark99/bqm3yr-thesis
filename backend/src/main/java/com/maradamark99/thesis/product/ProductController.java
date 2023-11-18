package com.maradamark99.thesis.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAll(@PageableDefault(sort={"createdAt"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
        return ResponseEntity.ok(productService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid ProductDTO productDTO) {
        var createdId = productService.create(productDTO);
        log.info("Product: {} successfully created with id {}", productDTO, createdId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdId);
    }
}

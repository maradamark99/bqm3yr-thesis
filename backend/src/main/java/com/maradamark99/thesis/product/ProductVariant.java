package com.maradamark99.thesis.product;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Table(name = "product_variants")
@Builder
@Getter @Setter
@EqualsAndHashCode
@ToString
public class ProductVariant {
    @Id
    @SequenceGenerator(name = "product_variant_sequence", sequenceName = "product_variant_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_sequence")
    Long id;

    @Column(nullable = false, length = 100, unique = true)
    String option;

    @ManyToOne
    Product product;

    @Column(name = "variant_specific_attributes", columnDefinition = "jsonb")
    Map<String, Object> variantSpecificAttributes;

    @Column(nullable = false)
    BigDecimal price;
}

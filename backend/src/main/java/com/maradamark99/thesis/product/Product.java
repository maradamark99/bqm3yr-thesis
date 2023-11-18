package com.maradamark99.thesis.product;

import com.maradamark99.thesis.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products", indexes = { @Index(columnList = "name"), @Index(columnList = "condition") })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Product {

        @Id
        @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
        long id;

        @Length(min = 3)
        @Column(nullable = false)
        String name;

        @NotBlank
        @Column(columnDefinition = "TEXT")
        String description;

        @ManyToMany(targetEntity = Category.class, fetch = FetchType.LAZY)
        @JoinTable(name = "product_categories", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
        List<Category> categories;

        @Column(nullable = false)
        @Enumerated(value = EnumType.STRING)
        ProductCondition condition;

        @Column(name = "current_price", nullable = false, precision = 10, scale = 3)
        BigDecimal currentPrice;

        @CreatedDate
        @Column(nullable = false, updatable = false)
        LocalDateTime createdAt;

        LocalDateTime updatedAt;

}

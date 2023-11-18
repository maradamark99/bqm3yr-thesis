package com.maradamark99.thesis.category;

import com.maradamark99.thesis.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Category {

    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    Long id;

    @NotBlank
    @Column(nullable = false, length = 100, unique = true)
    String value;

    @ManyToOne
    @JoinColumn(name = "parent_category")
    protected Category parent;

    @OneToMany(mappedBy = "parent")
    protected List<Category> children;

    @ManyToMany(mappedBy = "categories")
    List<Product> products;

    // TODO: add images/icons

}

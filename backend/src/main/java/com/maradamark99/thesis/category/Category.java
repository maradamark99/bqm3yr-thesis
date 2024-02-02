package com.maradamark99.thesis.category;

import com.maradamark99.thesis.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category {

    @Id
    @SequenceGenerator(name = "category_sequence", sequenceName = "category_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100, unique = true)
    private String value;

    @ManyToOne
    @JoinColumn(name = "parent_category")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

    @Transient
    public boolean isLeaf() {
        return children == null || children.isEmpty();
    }

    // TODO: add images/icons

}

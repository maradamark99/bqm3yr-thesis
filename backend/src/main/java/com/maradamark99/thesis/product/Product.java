package com.maradamark99.thesis.product;

import com.maradamark99.thesis.category.Category;
import com.maradamark99.thesis.media.Media;
import com.maradamark99.thesis.media.MediaType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products", indexes = {@Index(columnList = "name"), @Index(columnList = "condition")})
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
    private long id;

    @Length(min = 3)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(targetEntity = Category.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_media",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id")
    )
    private final Set<Media> media = new LinkedHashSet<>();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductCondition condition;

    @Column(name = "current_price", nullable = false, precision = 10, scale = 3)
    private BigDecimal currentPrice;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isListed;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        final var minImageCount = 3;
        if (this.isListed) {
            var imageCount = this.getMedia()
                    .stream()
                    .filter(m -> m.getMediaType() == MediaType.IMAGE)
                    .count();
            if (imageCount < minImageCount) {
                throw new IllegalStateException("Cannot update a listed product with less than " + minImageCount + " images");
            }
        }
    }

}

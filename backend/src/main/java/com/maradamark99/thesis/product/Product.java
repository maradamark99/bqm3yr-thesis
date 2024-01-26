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
import java.util.LinkedHashSet;
import java.util.Set;

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
    private long id;

    @Length(min = 3)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(targetEntity = Category.class, fetch = FetchType.LAZY)
    @JoinTable(name = "product_categories", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "product_media", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "media_id"))
    private final Set<Media> media = new LinkedHashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, optional = true)
    @JoinColumn(name = "thumbnail_image_id")
    private Media thumbnailImage;

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
            checkThumbnailImage();
            var imageCount = this.getMedia()
                    .stream()
                    .filter(m -> m.getMediaType() == MediaType.IMAGE)
                    .count();
            if (imageCount < minImageCount) {
                throw new IllegalStateException(
                        "Cannot update a listed product with less than " + minImageCount + " images");
            }
        }
    }

    private void checkThumbnailImage() {
        if (this.thumbnailImage == null) {
            this.thumbnailImage = this.getMedia()
                    .stream()
                    .filter(m -> m.getMediaType() == MediaType.IMAGE)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Product must have at least one image"));
        }
        if (!this.thumbnailImage.getMediaType().equals(MediaType.IMAGE)) {
            throw new IllegalStateException("Thumbnail image must be an image");
        }
    }

}

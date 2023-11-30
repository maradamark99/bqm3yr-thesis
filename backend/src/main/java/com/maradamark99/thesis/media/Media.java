package com.maradamark99.thesis.media;

import com.maradamark99.thesis.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@EqualsAndHashCode
public class Media {

    @Id
    @SequenceGenerator(name = "media_sequence", sequenceName = "media_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media_sequence")
    private long id;

    @NotBlank
    private String bucket;

    @NotBlank
    private String key;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MediaType mediaType;

    @NotBlank
    private String extension;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

}

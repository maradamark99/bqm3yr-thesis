package com.maradamark99.thesis.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductDTO {

    private long id;

    @NotBlank
    @Length(min = 3)
    private String name;

    @NotBlank
    private String description;

    // TODO: add enum validation
    @NotNull
    private ProductCondition condition;

    List<Long> categories;

    private String thumbnailUrl;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 3)
    private BigDecimal currentPrice;

    private LocalDateTime createdAt;

}

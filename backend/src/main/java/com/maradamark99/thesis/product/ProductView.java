package com.maradamark99.thesis.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductView {
    private long id;
    private String name;
    private String description;
    private ProductCondition condition;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
    private BigDecimal currentPrice;
    private List<String> mediaUrls;
}

package com.maradamark99.thesis.category;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@EqualsAndHashCode
@ToString
public class CategoryDTO {

    @Nullable
    private Long id;

    @NotBlank
    private String value;

    @Nullable
    private Long parentId;

}

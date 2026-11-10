package org.route.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Brand {
    @NotBlank
    private String name;
    @NotBlank
    private final String slug;
    @Builder.Default
    private boolean active =true;
}

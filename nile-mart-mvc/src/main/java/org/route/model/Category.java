package org.route.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @NotBlank(message = "Brand name is required")
    @Size(min = 2, max = 50,
            message = "Brand name must be between 2 and 50 characters")
    private String name;
    @NotBlank(message = "Slug is required")
    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
            message = "Slug must contain lowercase letters, digits, and single hyphens between them")
    private String slug;
    private String parentSlug;
    @NotBlank @PositiveOrZero
    private int displayOrder;
    @Builder.Default
    private boolean active=true;
}

package org.route.dto;

public record CategoryDisplay(
        String name,
        String slug,
        String parentName,
        Integer displayOrder,
        boolean active
) {}
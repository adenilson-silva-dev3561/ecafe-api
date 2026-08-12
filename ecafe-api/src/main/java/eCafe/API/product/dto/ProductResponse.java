package eCafe.API.product.dto;

import eCafe.API.common.enums.UnitType;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        Long categoryId,
        String categoryName,
        String name,
        String description,
        BigDecimal price,
        UnitType unitType,
        Integer stockQuantity,
        String imageUrl,
        Boolean active
) {
}


package com.api.server.product;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithOutImage {
    private long productId;
    private String name;
    private double price;
    private String description;
    @Enumerated(EnumType.STRING)
    private CategoryType category;
    private Integer numberInStock;
    private String supplier;
}
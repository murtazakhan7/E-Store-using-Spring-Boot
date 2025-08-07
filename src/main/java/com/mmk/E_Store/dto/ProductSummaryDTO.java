package com.mmk.E_Store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSummaryDTO {
   // private Long productId;
    private String name;
    private double price;
    // Removed: stock, description (internal details)
}

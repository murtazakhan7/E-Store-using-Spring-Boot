package com.mmk.E_Store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
    // private Long id;
    private Integer quantity;
    private ProductSummaryDTO product;
    private double price;
}
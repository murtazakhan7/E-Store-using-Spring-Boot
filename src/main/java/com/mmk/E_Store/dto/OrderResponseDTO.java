package com.mmk.E_Store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private String status;
    private double totalAmount;
    private List<OrderItemDTO> orderItems;
    private UserSummaryDTO user;
}
package com.mmk.E_Store.dto;

import com.mmk.E_Store.entity.OrderItems;
import com.mmk.E_Store.entity.Orders;
import com.mmk.E_Store.entity.Products;
import com.mmk.E_Store.entity.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponseDTO toOrderResponseDTO(Orders order) {
        return OrderResponseDTO.builder()
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .orderItems(order.getOrderItems() != null ?
                        order.getOrderItems().stream()
                                .map(this::toOrderItemDTO)
                                .collect(Collectors.toList()) : null)
                .user(order.getUser() != null ? toUserSummaryDTO(order.getUser()) : null)
                .build();
    }

    public List<OrderResponseDTO> toOrderResponseDTOList(List<Orders> orders) {
        return orders.stream()
                .map(this::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    private OrderItemDTO toOrderItemDTO(OrderItems orderItem) {
        return OrderItemDTO.builder()
                .quantity(orderItem.getQuantity())
                .product(orderItem.getProduct() != null ?
                        toProductSummaryDTO(orderItem.getProduct()) : null)
                .price(orderItem.getPrice())
                .build();
    }

    private ProductSummaryDTO toProductSummaryDTO(Products product) {
        return ProductSummaryDTO.builder()
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    private UserSummaryDTO toUserSummaryDTO(Users user) {
        return UserSummaryDTO.builder()
               // .id(user.getId())
                .name(user.getName())
                .build();
    }
}
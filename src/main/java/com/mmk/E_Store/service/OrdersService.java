

package com.mmk.E_Store.service;

import com.mmk.E_Store.dto.OrderMapper;
import com.mmk.E_Store.dto.OrderResponseDTO;
import com.mmk.E_Store.entity.OrderItems;
import com.mmk.E_Store.entity.Orders;
import com.mmk.E_Store.entity.Products;
import com.mmk.E_Store.entity.Users;
import com.mmk.E_Store.repository.OrdersRepo;
import com.mmk.E_Store.repository.ProductsRepo;
import com.mmk.E_Store.repository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepo orderRepository;
    private final UsersRepo userRepository;
    private final ProductsRepo productRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponseDTO createOrder(Orders orderRequest) {
        // Validate and fetch user
        Users user = userRepository.findById(orderRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderRequest.getUser().getId()));

        // Create new order
        Orders order = Orders.builder()
                .status(orderRequest.getStatus())
                .user(user)
                .orderItems(new ArrayList<>())
                .build();

        // Process order items
        if (orderRequest.getOrderItems() != null && !orderRequest.getOrderItems().isEmpty()) {
            for (OrderItems requestItem : orderRequest.getOrderItems()) {
                // Validate and fetch product
                Products product = productRepository.findById(requestItem.getProduct().getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found with id: " + requestItem.getProduct().getProductId()));

                // Check stock availability
                if (product.getStock() < requestItem.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }

                // Create order item
                OrderItems orderItem = OrderItems.builder()
                        .quantity(requestItem.getQuantity())
                        .product(product)
                        .price(product.getPrice())
                        .order(order)
                        .build();

                // Add to order using helper method
                order.addOrderItem(orderItem);

                // Update product stock
                product.setStock(product.getStock() - requestItem.getQuantity());
                productRepository.save(product);
            }
        }

        // Save order and convert to DTO
        Orders savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponseDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO findOrderById(Long orderId) {
        Orders order = orderRepository.findByIdWithDetails(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return orderMapper.toOrderResponseDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findOrdersByUserId(Long userId) {
        List<Orders> orders = orderRepository.findByUserIdOrderByOrderIdDesc(userId);
        return orderMapper.toOrderResponseDTOList(orders);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAllOrders() {
        List<Orders> orders = orderRepository.findAllByOrderByOrderIdDesc();
        return orderMapper.toOrderResponseDTOList(orders);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findOrdersByStatus(String status) {
        List<Orders> orders = orderRepository.findByStatus(status);
        return orderMapper.toOrderResponseDTOList(orders);
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, String status) {
        Orders order = orderRepository.findByIdWithDetails(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setStatus(status);
        Orders updatedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponseDTO(updatedOrder);
    }
}

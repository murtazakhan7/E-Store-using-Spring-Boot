

package com.mmk.E_Store.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Orders")
@Table(name = "Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    private Long orderId;

    @Column(
            name = "status",
            columnDefinition = "VARCHAR",
            nullable = false
    )
    private String status;

    private double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    @Builder.Default // Important for Lombok Builder
    private List<OrderItems> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private Users user;

    // Helper method to add order items and maintain bidirectional relationship
    public void addOrderItem(OrderItems orderItem) {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // Helper method to remove order items
    public void removeOrderItem(OrderItems orderItem) {
        if (orderItems != null) {
            orderItems.remove(orderItem);
            orderItem.setOrder(null);
        }
    }

    // Calculate total amount (removed @Transient since we want to persist it)
    public double calculateTotalAmount() {
        if (orderItems == null) {
            return 0.0;
        }
        return orderItems.stream()
                .filter(oi -> oi.getPrice() > 0 && oi.getQuantity() != null)
                .mapToDouble(oi -> oi.getPrice() * oi.getQuantity())
                .sum();
    }

    @PrePersist
    @PreUpdate
    private void updateTotalAmount() {
        this.totalAmount = calculateTotalAmount();
    }
}

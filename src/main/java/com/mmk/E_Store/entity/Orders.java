package com.mmk.E_Store.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItems> orderItems;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private Users user;



    @Transient
    public double getTotalAmount() {
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
        this.totalAmount = getTotalAmount();
    }


}

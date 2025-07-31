package com.mmk.E_Store.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "OrdersItems")
@Table(name = "OrdersItems")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItems {
    @Id
    @SequenceGenerator(
            name = "order_item_sequence",
            sequenceName = "order_item_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_item_sequence"
    )

    private Long id;
    private double price;
    private Integer quantity;

    @OneToOne(
            cascade = CascadeType.ALL

    )
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "ProductId"
    )
    private Products product;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "order_id",
            referencedColumnName = "OrderId"
    )
    private Orders order;
}

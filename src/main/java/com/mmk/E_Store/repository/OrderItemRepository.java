package com.mmk.E_Store.repository;

import com.mmk.E_Store.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

    List<OrderItems> findByOrderOrderId(Long orderId);

    List<OrderItems> findByProductProductId(Long productId);

    @Query("SELECT oi FROM OrderItems oi WHERE oi.order.orderId = :orderId")
    List<OrderItems> findOrderItemsByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT SUM(oi.quantity) FROM OrderItems oi WHERE oi.product.productId = :productId")
    Integer getTotalQuantityByProduct(@Param("productId") Long productId);
}
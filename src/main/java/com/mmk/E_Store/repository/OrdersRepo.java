
package com.mmk.E_Store.repository;

import com.mmk.E_Store.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long> {

    @Query("SELECT o FROM Orders o LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.product LEFT JOIN FETCH o.user WHERE o.orderId = :orderId")
    Optional<Orders> findByIdWithDetails(@Param("orderId") Long orderId);

    @Query("SELECT o FROM Orders o LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.product LEFT JOIN FETCH o.user WHERE o.user.id = :userId ORDER BY o.orderId DESC")
    List<Orders> findByUserIdOrderByOrderIdDesc(@Param("userId") Long userId);

    @Query("SELECT o FROM Orders o LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.product LEFT JOIN FETCH o.user ORDER BY o.orderId DESC")
    List<Orders> findAllByOrderByOrderIdDesc();

    List<Orders> findByStatus(String status);

    @Query("SELECT o FROM Orders o WHERE o.user.id = :userId AND o.status = :status ORDER BY o.orderId DESC")
    List<Orders> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);
}

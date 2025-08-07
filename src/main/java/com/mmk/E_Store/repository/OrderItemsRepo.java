package com.mmk.E_Store.repository;

import com.mmk.E_Store.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepo extends JpaRepository<OrderItems, Long> {

} 
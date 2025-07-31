package com.mmk.E_Store.repository;

import com.mmk.E_Store.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<Orders,Long>{

}

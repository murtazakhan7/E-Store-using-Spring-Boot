package com.mmk.E_Store.service;

import com.mmk.E_Store.entity.OrderItems;
import com.mmk.E_Store.entity.Orders;
import com.mmk.E_Store.exception.OrderNotFoundException;
import com.mmk.E_Store.exception.UserNotFoundException;
import com.mmk.E_Store.repository.OrdersRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepo ordersRepo;

    public List<Orders> getAllOrders(){
        return ordersRepo.findAll();
    }

    public Orders saveOrder(Orders order) {
        return ordersRepo.save(order);
    }

    public Orders getOrderById(Long id) {
        return ordersRepo.findById(id)
                .orElseThrow(() ->new OrderNotFoundException("Order not found with id " + id));

    }

    public void deleteOrder(Long id) {
        if (!ordersRepo.existsById(id)) {
            throw new EntityNotFoundException("Order not found with id: " + id);
        }
        ordersRepo.deleteById(id);
    }

    public Orders updateOrder(Long id, Orders order) {
        return ordersRepo.findById(id)
                .map(existingEntity -> {

                    existingEntity.setStatus(order.getStatus());
                    existingEntity.setOrder_item(order.getOrder_item());
                    existingEntity.setTotal_amount(calculuateTotalAmount(order.getOrder_item()));


                    return ordersRepo.save(existingEntity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Entity with Id"+id+ "not found"));

    }

    private static double calculuateTotalAmount(List<OrderItems> items) {
        return items.stream()
                .mapToDouble(OrderItems::getPrice)
                .sum();
    }

}

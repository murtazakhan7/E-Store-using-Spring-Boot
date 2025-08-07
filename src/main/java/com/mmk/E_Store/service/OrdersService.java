package com.mmk.E_Store.service;

import com.mmk.E_Store.entity.OrderItems;
import com.mmk.E_Store.entity.Orders;
import com.mmk.E_Store.exception.OrderNotFoundException;
import com.mmk.E_Store.exception.UserNotFoundException;
import com.mmk.E_Store.repository.OrdersRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepo ordersRepo;

    public List<Orders> getAllOrders(){
        return ordersRepo.findAll();
    }

//    public ResponseEntity<Orders> saveOrder(Orders order) {
//        return ordersRepo.save(order);
//    }
//    public Orders saveOrder(Orders order) {
//        // ensure each OrderItems points back to this Orders instance
//        if (order.getOrder_item() != null) {
//            for (OrderItems item : order.getOrder_item()) {
//                item.setOrder(order);
//            }
//        }
//        return ordersRepo.save(order);
//    }
    public Orders saveOrder(Orders order) {
        if (order.getOrderItems() != null) {
            for (OrderItems item : order.getOrderItems()) {
                item.setOrder(order);
            }
        }
        return ordersRepo.save(order);
    }

        public List<Orders> saveAllOrders(List<Orders> orders) {
            return ordersRepo.saveAll(orders);
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
                    existingEntity.setOrderItems(order.getOrderItems());
                    existingEntity.setTotalAmount(order.getTotalAmount());
                    if (existingEntity.getOrderItems() != null) {
                        for (OrderItems item : existingEntity.getOrderItems()) {
                            item.setOrder(existingEntity);
                        }
                    }
                    return ordersRepo.save(existingEntity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Entity with Id"+id+ "not found"));

    }


}

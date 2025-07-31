package com.mmk.E_Store.controller;

import com.mmk.E_Store.entity.Orders;
import com.mmk.E_Store.service.OrdersService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public List<Orders> getAllOrders(){
        return ordersService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Orders getOrderById(@PathVariable Long id){
        return ordersService.getOrderById(id);

    }

    @PostMapping
    public Orders saveOrder(@RequestBody Orders order){
        return ordersService.saveOrder(order);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders order){
        try{
            Orders updatedOrder = ordersService.updateOrder(id,order);
            return ResponseEntity.ok(updatedOrder);

        }
        catch (EntityNotFoundException e){
            return  ResponseEntity.notFound().build();
        }
    }

}

package com.mmk.E_Store.controller;

import com.mmk.E_Store.entity.Orders;
import com.mmk.E_Store.service.OrdersService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders order = ordersService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Orders> saveOrder(@RequestBody Orders order) {
        Orders saved = ordersService.saveOrder(order);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getOrderId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Orders>> saveOrders(@RequestBody List<Orders> orders) {
        List<Orders> savedOrders = ordersService.saveAllOrders(orders);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrders);
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

package com.mmk.E_Store.controller;


import com.mmk.E_Store.entity.Products;
import com.mmk.E_Store.service.ProductsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import  java.util.List;

@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired
    private ProductsService productService;

    @GetMapping
    public List<Products> getProducts(){
       return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Products getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    public Products saveProduct(@RequestBody Products product){
        return productService.saveProduct(product);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<Products> updateProduct(
            @PathVariable Long id,
            @RequestBody Products productPayload
    ) {
        try {
            Products updated = productService.updateProduct(id, productPayload);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}

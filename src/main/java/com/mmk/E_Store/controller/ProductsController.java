package com.mmk.E_Store.controller;


import com.mmk.E_Store.entity.Products;
import com.mmk.E_Store.service.ProductsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import  java.util.List;

@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired
    private ProductsService productService;

    @GetMapping
    public ResponseEntity<List<Products>> getProducts(){
       List<Products> allProducts = productService.getProducts();
       return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Long id){
        Products singleProduct =  productService.getProductById(id);
        return singleProduct !=null ? ResponseEntity.ok(singleProduct) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Products> saveProduct(@RequestBody Products product){
        Products saveProduct =  productService.saveProduct(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveProduct.getProductId())
                .toUri();
        return ResponseEntity.created(location).body(saveProduct);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Products>> saveProducts(@RequestBody List<Products> products){
        List<Products> bulkSave =  productService.saveProducts(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(bulkSave);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
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

package com.mmk.E_Store.service;

import com.mmk.E_Store.entity.Products;
import com.mmk.E_Store.exception.ProductNotFoundException;
import com.mmk.E_Store.repository.ProductsRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepo productsRepo;

    public List<Products> getProducts() {
        return productsRepo.findAll();
    }

    public Products getProductById(long id){
        return  productsRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
    }

    public Products saveProduct(Products product) {
        return productsRepo.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productsRepo.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productsRepo.deleteById(id);
    }
    public Products updateProduct(Long id, Products product) {
        return productsRepo.findById(id)
                .map(existing -> {
                    existing.setName(product.getName());
                    existing.setPrice(product.getPrice());
                    existing.setStock(product.getStock());
                    existing.setDescription(product.getDescription());
                    return productsRepo.save(existing);
                })
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found with id: " + id)
                );
    }

}

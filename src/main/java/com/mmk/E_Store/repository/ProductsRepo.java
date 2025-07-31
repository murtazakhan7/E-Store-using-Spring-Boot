package com.mmk.E_Store.repository;

import com.mmk.E_Store.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo extends JpaRepository<Products,Long> {
}

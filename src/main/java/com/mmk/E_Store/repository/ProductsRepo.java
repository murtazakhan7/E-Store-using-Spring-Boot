
package com.mmk.E_Store.repository;

import com.mmk.E_Store.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {

    Optional<Products> findByName(String name);

    List<Products> findByPriceBetween(double minPrice, double maxPrice);

    List<Products> findByStockGreaterThan(int stock);

    @Query("SELECT p FROM Products p WHERE p.stock >= :quantity")
    List<Products> findAvailableProducts(@Param("quantity") int quantity);

    @Query("SELECT p FROM Products p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Products> findByNameContainingIgnoreCase(@Param("name") String name);
}

package com.example.ecommerce.repo;

import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByCategory(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByProductNameContainingIgnoreCase(String productName);
}

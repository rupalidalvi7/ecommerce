package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequest;
import com.example.ecommerce.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse saveProduct(ProductRequest request);

    ProductResponse getProduct(Long id);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

}
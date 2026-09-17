package com.example.ecommerce.serviceImp;


import com.example.ecommerce.dto.ProductRequest;
import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repo.ProductRepo;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponse saveProduct(ProductRequest request) {

        Product product = modelMapper.map(request, Product.class);

        Product savedProduct = productRepo.save(product);

        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    @Override
    public ProductResponse getProduct(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with Id : " + id));

        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        List<Product> products = productRepo.findAll();

        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with Id : " + id));

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setBrand(request.getBrand());
        product.setCategory(request.getCategory());

        Product updatedProduct = productRepo.save(product);

        return modelMapper.map(updatedProduct, ProductResponse.class);
    }
    @Override
    public void deleteProduct(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with Id : " + id));

        productRepo.delete(product);
    }

}

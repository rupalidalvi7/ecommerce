package com.example.ecommerce.service;

import com.example.ecommerce.dto.CartRequest;
import com.example.ecommerce.dto.CartResponse;

import java.util.List;

public interface CartService {

    CartResponse addToCart(CartRequest request);

    List<CartResponse> getCustomerCart(Long customerId);

    CartResponse updateCart(Long cartId, CartRequest request);

    void removeFromCart(Long cartId);

    void clearCart(Long customerId);

}

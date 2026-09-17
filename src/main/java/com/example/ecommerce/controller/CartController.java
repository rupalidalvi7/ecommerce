package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CartRequest;
import com.example.ecommerce.dto.CartResponse;
import com.example.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse> addToCart(
            @Valid @RequestBody CartRequest request) {

        CartResponse response = cartService.addToCart(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CartResponse>> getCustomerCart(
            @PathVariable Long customerId) {

        List<CartResponse> response = cartService.getCustomerCart(customerId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<CartResponse> updateCart(
            @PathVariable Long cartId,
            @Valid @RequestBody CartRequest request) {

        CartResponse response = cartService.updateCart(cartId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> removeFromCart(
            @PathVariable Long cartId) {

        cartService.removeFromCart(cartId);

        return ResponseEntity.ok("Item removed from cart successfully.");
    }

    @DeleteMapping("/clear/{customerId}")
    public ResponseEntity<String> clearCart(
            @PathVariable Long customerId) {

        cartService.clearCart(customerId);

        return ResponseEntity.ok("Cart cleared successfully.");
    }

}

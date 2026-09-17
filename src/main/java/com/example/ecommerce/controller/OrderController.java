package com.example.ecommerce.controller;

import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.dto.OrderResponse;
import com.example.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @Valid @RequestBody OrderRequest request) {

        OrderResponse response = orderService.placeOrder(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable Long id) {

        OrderResponse response = orderService.getOrder(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getCustomerOrders(
            @PathVariable Long customerId) {

        List<OrderResponse> response =
                orderService.getCustomerOrders(customerId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelOrder(
            @PathVariable Long id) {

        orderService.cancelOrder(id);

        return ResponseEntity.ok("Order cancelled successfully.");
    }

}

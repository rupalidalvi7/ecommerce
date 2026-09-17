package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

    OrderResponse getOrder(Long id);

    List<OrderResponse> getCustomerOrders(Long customerId);

    void cancelOrder(Long id);

}

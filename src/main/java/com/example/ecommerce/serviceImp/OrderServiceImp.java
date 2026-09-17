package com.example.ecommerce.serviceImp;

import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.dto.OrderResponse;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Custmer;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderStatus;
import com.example.ecommerce.repo.CartRepo;
import com.example.ecommerce.repo.CustmRepo;
import com.example.ecommerce.repo.OrderRepo;
import com.example.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderRepo orderRepo;
    private final CustmRepo custmRepo;
    private final CartRepo cartRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {

        Custmer custmer = custmRepo.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with Id : "
                                        + request.getCustomerId()));

        List<Cart> carts = cartRepo.findByCustomer(custmer);

        if (carts.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Cart is empty for customer Id : "
                            + request.getCustomerId());
        }

        BigDecimal totalAmount = carts.stream()
                .map(cart ->
                        cart.getProduct()
                                .getPrice()
                                .multiply(
                                        BigDecimal.valueOf(cart.getQuantity())
                                )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();

        order.setCustomer(custmer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepo.save(order);

        return modelMapper.map(savedOrder, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrder(Long id) {

        Order order = orderRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with Id : " + id));

        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public List<OrderResponse> getCustomerOrders(Long customerId) {

        Custmer custmer = custmRepo.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with Id : " + customerId));

        List<Order> orders = orderRepo.findByCustomer(custmer);

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(Long id) {

        Order order = orderRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with Id : " + id));

        order.setStatus(OrderStatus.CANCELLED);

        orderRepo.save(order);
    }
}
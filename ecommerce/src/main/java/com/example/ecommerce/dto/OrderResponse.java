package com.example.ecommerce.dto;

import com.example.ecommerce.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private LocalDateTime orderDate;

    private OrderStatus status;

    private BigDecimal totalAmount;

}

package com.example.ecommerce.service;

import com.example.ecommerce.dto.PaymentRequest;
import com.example.ecommerce.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest request);

    PaymentResponse getPayment(Long id);

}
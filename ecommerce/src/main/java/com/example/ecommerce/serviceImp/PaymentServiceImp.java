package com.example.ecommerce.serviceImp;

import com.example.ecommerce.dto.PaymentRequest;
import com.example.ecommerce.dto.PaymentResponse;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.model.PaymentStatus;
import com.example.ecommerce.repo.OrderRepo;
import com.example.ecommerce.repo.PaymentRepo;
import com.example.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;
    private final ModelMapper modelMapper;

    @Override
    public PaymentResponse makePayment(PaymentRequest request) {

        Order order = orderRepo.findById(request.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with Id : " + request.getOrderId()));

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepo.save(payment);

        return modelMapper.map(savedPayment, PaymentResponse.class);
    }

    @Override
    public PaymentResponse getPayment(Long id) {

        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with Id : " + id));

        return modelMapper.map(payment, PaymentResponse.class);
    }
}

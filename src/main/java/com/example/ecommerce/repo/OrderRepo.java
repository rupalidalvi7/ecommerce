package com.example.ecommerce.repo;

import com.example.ecommerce.model.Custmer;
import com.example.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByCustomer(Custmer custmer);
}

package com.example.ecommerce.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

    @Entity
    @Getter
    @Setter
    public class Cart extends BaseEntity {

        @Column(nullable = false)
        private Integer quantity;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id", nullable = false)
        private Custmer customer;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;
    }

package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

    @Getter
    @Setter
    @Entity
    public class Product extends BaseEntity {

        @Column(nullable = false)
        private String productName;

        @Column(length = 1000)
        private String description;

        @Column(nullable = false)
        private BigDecimal price;

        @Column(nullable = false)
        private String brand;

        @Column(nullable = false)
        private String category;


        @ManyToOne
        @JoinColumn(name = "seller_id")
        private Saller seller;
    }


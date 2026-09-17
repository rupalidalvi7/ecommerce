package com.example.ecommerce.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Saller extends User {

    @Column(nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String businessAddress;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> products;

}

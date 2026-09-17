package com.example.ecommerce.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Admin extends User{



    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String designation;

}

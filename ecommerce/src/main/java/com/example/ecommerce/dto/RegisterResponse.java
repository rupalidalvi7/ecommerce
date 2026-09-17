package com.example.ecommerce.dto;
import com.example.ecommerce.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String gender;

    private String address;

    private Role role;

    private String message;

}
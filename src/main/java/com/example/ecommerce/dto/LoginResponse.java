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
public class LoginResponse {

    private Long id;

    private String name;

    private String email;

    private Role role;

    private String token;

    private String message;

}
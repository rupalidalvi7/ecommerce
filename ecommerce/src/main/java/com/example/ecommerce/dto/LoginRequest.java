package com.example.ecommerce.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim().toLowerCase();
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}
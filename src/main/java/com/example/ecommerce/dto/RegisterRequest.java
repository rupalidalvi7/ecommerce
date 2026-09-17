package com.example.ecommerce.dto;

import com.example.ecommerce.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    // Common Fields

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters")
    @Pattern(
            regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$",
            message = "Full name must contain only letters and single spaces"
    )
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$",
            message = "Password must contain uppercase, lowercase, number and special character"
    )
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Enter a valid 10-digit mobile number"
    )
    private String phone;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Role is required")
    private Role role;

    // Admin Fields

    private String department;

    private String designation;

    // Custmer Fields

    private String city;

    private String state;

    private String pincode;

    // Saller Fields

    private String shopName;

    private String businessAddress;

    // Trim Methods

    public void setname(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim().toLowerCase();
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public void setDesignation(String designation) {
        this.designation = designation == null ? null : designation.trim();
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public void setPincode(String pincode) {
        this.pincode = pincode == null ? null : pincode.trim();
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress == null ? null : businessAddress.trim();
    }
}
package com.example.ecommerce.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9]+(?:\\s[A-Za-z0-9]+)*$",
            message = "Product name contains invalid characters"
    )
    private String productName;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Brand is required")
    @Pattern(
            regexp = "^[A-Za-z0-9]+(?:\\s[A-Za-z0-9]+)*$",
            message = "Brand contains invalid characters"
    )
    private String brand;

    @NotBlank(message = "Category is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Category must contain only letters"
    )
    private String category;

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }
}
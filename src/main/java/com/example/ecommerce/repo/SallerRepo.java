package com.example.ecommerce.repo;

import com.example.ecommerce.model.Saller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SallerRepo extends JpaRepository<Saller,Long> {
    Optional<Saller> findByEmail(String email);
}

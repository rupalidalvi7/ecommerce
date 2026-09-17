package com.example.ecommerce.controller;

import com.example.ecommerce.model.Admin;
import com.example.ecommerce.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdmin(
            @PathVariable Long id) {

        Admin admin = adminService.getAdmin(id);

        return ResponseEntity.ok(admin);
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {

        List<Admin> admins = adminService.getAllAdmins();

        return ResponseEntity.ok(admins);
    }

}
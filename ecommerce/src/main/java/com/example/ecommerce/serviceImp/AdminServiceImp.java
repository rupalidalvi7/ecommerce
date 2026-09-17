package com.example.ecommerce.serviceImp;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Admin;
import com.example.ecommerce.repo.AdminRepo;
import com.example.ecommerce.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {

    private final AdminRepo adminRepo;

    @Override
    public Admin getAdmin(Long id) {

        return adminRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin not found with Id : " + id));
    }

    @Override
    public List<Admin> getAllAdmins() {

        return adminRepo.findAll();
    }

}
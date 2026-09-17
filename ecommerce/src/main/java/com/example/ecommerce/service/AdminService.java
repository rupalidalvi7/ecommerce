package com.example.ecommerce.service;

import com.example.ecommerce.model.Admin;

import java.util.List;

public interface AdminService {

    Admin getAdmin(Long id);

    List<Admin> getAllAdmins();

}

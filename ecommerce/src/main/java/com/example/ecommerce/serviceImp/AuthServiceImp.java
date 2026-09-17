package com.example.ecommerce.serviceImp;

import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.dto.LoginResponse;
import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.dto.RegisterResponse;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Admin;
import com.example.ecommerce.model.Custmer;
import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.Saller;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.AdminRepo;
import com.example.ecommerce.repo.CustmRepo;
import com.example.ecommerce.repo.SallerRepo;
import com.example.ecommerce.repo.UserRepo;
import com.example.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepo userRepo;
    private final AdminRepo adminRepo;
    private final CustmRepo custmRepo;
    private final SallerRepo sallerRepo;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new ResourceNotFoundException("Email already registered.");
        }

        RegisterResponse response = new RegisterResponse();

        switch (request.getRole()) {

            case CUSTOMER:

                Custmer custmer = new Custmer();

                custmer.setName(request.getName());
                custmer.setEmail(request.getEmail());
                custmer.setPassword(passwordEncoder.encode(request.getPassword()));
                custmer.setPhone(request.getPhone());
                custmer.setGender(request.getGender());
                custmer.setAddress(request.getAddress());
                custmer.setRole(Role.CUSTOMER);

                custmer.setCity(request.getCity());
                custmer.setState(request.getState());
                custmer.setPincode(request.getPincode());

                Custmer savedCustmer = custmRepo.save(custmer);

                response.setId(savedCustmer.getId());
                response.setName(savedCustmer.getName());
                response.setEmail(savedCustmer.getEmail());
                response.setRole(savedCustmer.getRole());
                response.setMessage("Customer Registered Successfully");

                return response;

            case SELLER:

                Saller saller = new Saller();

                saller.setName(request.getName());
                saller.setEmail(request.getEmail());
                saller.setPassword(passwordEncoder.encode(request.getPassword()));
                saller.setPhone(request.getPhone());
                saller.setGender(request.getGender());
                saller.setAddress(request.getAddress());
                saller.setRole(Role.SELLER);

                saller.setShopName(request.getShopName());
                saller.setBusinessAddress(request.getBusinessAddress());

                Saller savedSaller = sallerRepo.save(saller);

                response.setId(savedSaller.getId());
                response.setName(savedSaller.getName());
                response.setEmail(savedSaller.getEmail());
                response.setRole(savedSaller.getRole());
                response.setMessage("Seller Registered Successfully");

                return response;

            case ADMIN:

                throw new ResourceNotFoundException("Admin registration is not allowed.");

            default:

                throw new ResourceNotFoundException("Invalid Role.");
        }
    }
    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid Email or Password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid Email or Password");
        }

        LoginResponse response = new LoginResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        // JWT will be added later
        response.setToken("Login Successful");

        response.setMessage("Welcome " + user.getName());

        return response;
    }

}
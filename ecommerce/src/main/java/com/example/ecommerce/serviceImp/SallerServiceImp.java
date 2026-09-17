package com.example.ecommerce.serviceImp;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Saller;
import com.example.ecommerce.repo.SallerRepo;
import com.example.ecommerce.service.SallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SallerServiceImp implements SallerService {

    private final SallerRepo sallerRepo;

    @Override
    public Saller getSaller(Long id) {

        return sallerRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Seller not found with Id : " + id));
    }

    @Override
    public List<Saller> getAllSallers() {

        return sallerRepo.findAll();
    }

    @Override
    public void deleteSaller(Long id) {

        Saller saller = sallerRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Seller not found with Id : " + id));

        sallerRepo.delete(saller);
    }
}

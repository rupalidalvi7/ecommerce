package com.example.ecommerce.serviceImp;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Custmer;
import com.example.ecommerce.repo.CustmRepo;
import com.example.ecommerce.service.CustmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustmerServiceImp implements CustmerService {

    private final CustmRepo custmRepo;

    @Override
    public Custmer getCustmer(Long id) {

        return custmRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with Id : " + id));
    }

    @Override
    public List<Custmer> getAllCustmers() {

        return custmRepo.findAll();
    }

    @Override
    public void deleteCustmer(Long id) {

        Custmer custmer = custmRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with Id : " + id));

        custmRepo.delete(custmer);
    }
}

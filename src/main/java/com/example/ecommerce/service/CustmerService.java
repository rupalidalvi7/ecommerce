package com.example.ecommerce.service;


import com.example.ecommerce.model.Custmer;

import java.util.List;

public interface CustmerService {

    Custmer getCustmer(Long id);

    List<Custmer> getAllCustmers();

    void deleteCustmer(Long id);

}
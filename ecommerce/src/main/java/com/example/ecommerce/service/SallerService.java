package com.example.ecommerce.service;


import com.example.ecommerce.model.Saller;

import java.util.List;

public interface SallerService {

    Saller getSaller(Long id);

    List<Saller> getAllSallers();

    void deleteSaller(Long id);

}

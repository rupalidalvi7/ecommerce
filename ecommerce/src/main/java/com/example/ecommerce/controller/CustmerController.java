package com.example.ecommerce.controller;

import com.example.ecommerce.model.Custmer;
import com.example.ecommerce.service.CustmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustmerController {

    private final CustmerService custmerService;

    @GetMapping("/{id}")
    public ResponseEntity<Custmer> getCustmer(
            @PathVariable Long id) {

        Custmer custmer = custmerService.getCustmer(id);

        return ResponseEntity.ok(custmer);
    }

    @GetMapping
    public ResponseEntity<List<Custmer>> getAllCustmers() {

        List<Custmer> custmers = custmerService.getAllCustmers();

        return ResponseEntity.ok(custmers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustmer(
            @PathVariable Long id) {

        custmerService.deleteCustmer(id);

        return ResponseEntity.ok("Customer deleted successfully.");
    }

}

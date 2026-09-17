package com.example.ecommerce.controller;



import com.example.ecommerce.model.Saller;
import com.example.ecommerce.service.SallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
public class SallerController {

    private final SallerService sallerService;

    @GetMapping("/{id}")
    public ResponseEntity<Saller> getSaller(
            @PathVariable Long id) {

        Saller saller = sallerService.getSaller(id);

        return ResponseEntity.ok(saller);
    }

    @GetMapping
    public ResponseEntity<List<Saller>> getAllSallers() {

        List<Saller> sallers = sallerService.getAllSallers();

        return ResponseEntity.ok(sallers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSaller(
            @PathVariable Long id) {

        sallerService.deleteSaller(id);

        return ResponseEntity.ok("Seller deleted successfully.");
    }

}

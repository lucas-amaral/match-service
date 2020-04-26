package com.proposta.aceita.matchservice.controllers;

import com.proposta.aceita.matchservice.entities.Sale;
import com.proposta.aceita.matchservice.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<?> post(@Validated @RequestBody Sale body) {
        return saleService.save(body)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.proposta.aceita.matchservice.controllers;

import com.proposta.aceita.matchservice.services.NegotiationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/negotiations")
public class NegotiationController {

    private final NegotiationService negotiationService;

    @Autowired
    public NegotiationController(NegotiationService negotiationService) {
        this.negotiationService = negotiationService;
    }

    @GetMapping("/sales/{saleId}")
    public ResponseEntity<?> getBySales(@PathVariable Integer saleId) {
        return negotiationService.getNegotiationBySaleId(saleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping("/interests/{interestId}")
    public ResponseEntity<?> getByInterest(@PathVariable Integer interestId) {
        return negotiationService.getNegotiationByInterestId(interestId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping("/{id}/approved_by_seller")
    public ResponseEntity<?> approvedBySeller(@PathVariable String id) {
        negotiationService.approvedBySeller(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/approved_by_buyer")
    public ResponseEntity<?> approvedByBuyer(@PathVariable String id) {
        negotiationService.approvedByBuyer(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/reproved_by_seller")
    public ResponseEntity<?> reprovedBySeller(@PathVariable String id) {
        negotiationService.reprovedBySeller(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/reproved_by_buyer")
    public ResponseEntity<?> reprovedByBuyer(@PathVariable String id) {
        negotiationService.reprovedByBuyer(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        negotiationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
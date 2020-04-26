package com.proposta.aceita.matchservice.controllers;

import com.proposta.aceita.matchservice.entities.Interest;
import com.proposta.aceita.matchservice.services.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/interests")
public class InterestController {

    private final InterestService interestService;

    @Autowired
    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    @PostMapping
    public ResponseEntity<?> post(@Validated @RequestBody Interest body) {
        return interestService.save(body)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        interestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

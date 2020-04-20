package com.proposta.aceita.matchservice.services;

import com.proposta.aceita.matchservice.entities.Sale;
import com.proposta.aceita.matchservice.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final NegotiationService negotiationService;

    @Autowired
    public SaleService(SaleRepository saleRepository, NegotiationService negotiationService) {
        this.saleRepository = saleRepository;
        this.negotiationService = negotiationService;
    }

    public Optional<Sale> save(Sale sale) {

        negotiationService.findAsyncMatches(sale);

        return Optional.of(saleRepository.save(sale));
    }

    public void delete(Integer id) {

        saleRepository.deleteById(id);

        negotiationService.getNegotiationBySaleId(id).ifPresent(negotiationService::delete);
    }
}

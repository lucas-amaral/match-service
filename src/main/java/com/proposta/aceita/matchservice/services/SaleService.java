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

        ifAlreadyExistsRemoveOldNegotiations(sale.getId());

        negotiationService.findAsyncMatches(sale);

        return Optional.of(saleRepository.save(sale));
    }

    private void ifAlreadyExistsRemoveOldNegotiations(Integer saleId) {
        saleRepository.findById(saleId)
                .ifPresent(interest -> negotiationService.getNegotiationBySaleId(saleId)
                        .forEach(negotiation -> negotiationService.reprovedBySeller(negotiation.getId())));
    }


    public void delete(Integer id) {

        saleRepository.deleteById(id);

        negotiationService.getNegotiationBySaleId(id).forEach(negotiationService::delete);
        negotiationService.getApprovedNegotiationBySaleId(id).forEach(negotiationService::delete);
    }
}

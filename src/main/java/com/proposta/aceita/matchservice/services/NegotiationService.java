package com.proposta.aceita.matchservice.services;

import com.proposta.aceita.matchservice.entities.*;
import com.proposta.aceita.matchservice.entities.enums.NegotiationStatus;
import com.proposta.aceita.matchservice.repositories.NegotiationApprovedBySellerRepository;
import com.proposta.aceita.matchservice.repositories.NegotiationClosedRepository;
import com.proposta.aceita.matchservice.repositories.NegotiationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.proposta.aceita.matchservice.entities.enums.NegotiationStatus.*;

@Service
public class NegotiationService {

    private final NegotiationRepository negotiationRepository;
    private final NegotiationApprovedBySellerRepository negotiationApprovedBySellerRepository;
    private final NegotiationClosedRepository negotiationClosedRepository;

    @Autowired
    public NegotiationService(NegotiationRepository negotiationRepository, NegotiationApprovedBySellerRepository negotiationApprovedBySellerRepository, NegotiationClosedRepository negotiationClosedRepository) {
        this.negotiationRepository = negotiationRepository;
        this.negotiationApprovedBySellerRepository = negotiationApprovedBySellerRepository;
        this.negotiationClosedRepository = negotiationClosedRepository;
    }

    public List<Negotiation> getNegotiationBySaleId(Integer saleId) {
        return negotiationRepository.findBySaleId(saleId);
    }

    public List<Negotiation> getNegotiationByInterestId(Integer interestId) {
        return negotiationRepository.findByInterestId(interestId);
    }

    public void delete(String id) {
        negotiationRepository.deleteById(id);
    }

    public void delete(Negotiation negotiation) {
        negotiationRepository.delete(negotiation);
    }

    public void findAsyncMatches(Sale sale) {
        CompletableFuture<?> completableFuture = CompletableFuture.runAsync(() -> findMatches(sale));
        try {
            completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    void findMatches(Sale sale) {
        var negotiations = negotiationRepository.findInterestsBySale(sale).stream()
                .map(interest -> Negotiation.of(interest, sale)).collect(Collectors.toList());

        negotiationRepository.save(negotiations);

        //todo: send email for each negotiation
    }

    public void findAsyncMatches(Interest interest) {
        CompletableFuture<?> completableFuture = CompletableFuture.runAsync(() -> findMatches(interest));
        try {
            completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    void findMatches(Interest interest) {
        var negotiations = negotiationRepository.findSalesByInterest(interest).stream()
                .map(sale -> Negotiation.of(interest, sale)).collect(Collectors.toList());

        negotiationRepository.save(negotiations);

        //todo: send email for each negotiation

    }

    public void approvedBySeller(String id) {
        negotiationRepository.findById(id).ifPresent(negotiation -> {
            negotiationApprovedBySellerRepository.save(NegotiationApprovedBySeller.of(negotiation));

            negotiationRepository.delete(negotiation);

            //todo: send email to buyer
        });
    }

    public void approvedByBuyer(String id) {
        negotiationApprovedBySellerRepository.findById(id).ifPresent(negotiation -> {
            negotiationClosedRepository.save(NegotiationClosed.of(negotiation, FINISHED));

            negotiationApprovedBySellerRepository.delete(negotiation);

            //todo: send email to seller
        });
    }

    public void reprovedBySeller(String id) {
        negotiationRepository.findById(id).ifPresent(negotiation -> {
            negotiationClosedRepository.save(NegotiationClosed.of(negotiation));

            negotiationRepository.delete(negotiation);

            //todo: send email to buyer
        });
    }

    public void reprovedByBuyer(String id) {
        negotiationApprovedBySellerRepository.findById(id).ifPresent(negotiation -> {
            negotiationClosedRepository.save(NegotiationClosed.of(negotiation, NOT_APPROVED_BY_THE_BUYER));

            negotiationApprovedBySellerRepository.delete(negotiation);

            //todo: send email to seller
        });
    }
}

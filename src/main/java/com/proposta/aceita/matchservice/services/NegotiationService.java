package com.proposta.aceita.matchservice.services;

import com.proposta.aceita.matchservice.entities.*;
import com.proposta.aceita.matchservice.repositories.NegotiationApprovedBySellerRepository;
import com.proposta.aceita.matchservice.repositories.NegotiationClosedRepository;
import com.proposta.aceita.matchservice.repositories.NegotiationRepository;
import com.proposta.aceita.matchservice.services.integrations.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.proposta.aceita.matchservice.entities.enums.NegotiationStatus.FINISHED;
import static com.proposta.aceita.matchservice.entities.enums.NegotiationStatus.NOT_APPROVED_BY_THE_BUYER;

@Service
public class NegotiationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NegotiationService.class);

    private final NegotiationRepository negotiationRepository;
    private final NegotiationApprovedBySellerRepository negotiationApprovedBySellerRepository;
    private final NegotiationClosedRepository negotiationClosedRepository;
    private final NotificationService notificationService;

    @Autowired
    public NegotiationService(NegotiationRepository negotiationRepository, NegotiationApprovedBySellerRepository negotiationApprovedBySellerRepository, NegotiationClosedRepository negotiationClosedRepository, NotificationService notificationService) {
        this.negotiationRepository = negotiationRepository;
        this.negotiationApprovedBySellerRepository = negotiationApprovedBySellerRepository;
        this.negotiationClosedRepository = negotiationClosedRepository;
        this.notificationService = notificationService;
    }

    public List<Negotiation> getNegotiationBySaleId(Integer saleId) {
        return negotiationRepository.findBySaleId(saleId);
    }

    public List<Negotiation> getNegotiationByInterestId(Integer interestId) {
        return negotiationRepository.findByInterestId(interestId);
    }

    public List<NegotiationApprovedBySeller> getApprovedNegotiationBySaleId(Integer saleId) {
        return negotiationRepository.findApprovedBySaleId(saleId);
    }

    public List<NegotiationApprovedBySeller> getApprovedNegotiationByInterestId(Integer interestId) {
        return negotiationRepository.findApprovedByInterestId(interestId);
    }

    public void delete(String id) {
        negotiationRepository.deleteById(id);
    }

    public void delete(Negotiation negotiation) {
        negotiationRepository.delete(negotiation);
    }

    public void delete(NegotiationApprovedBySeller negotiation) {
        negotiationApprovedBySellerRepository.delete(negotiation);
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
        LOGGER.info("Start match evaluator: {}", sale);
        var negotiations = negotiationRepository.findInterestsBySale(sale).stream()
                .map(interest -> Negotiation.of(interest, sale)).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(negotiations)) {
            negotiationRepository.save(negotiations);

            negotiations.forEach(notificationService::sendMatchEmailForSeller);
        }
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
        LOGGER.info("Start match evaluator: {}", interest);
        var negotiations = negotiationRepository.findSalesByInterest(interest).stream()
                .map(sale -> Negotiation.of(interest, sale)).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(negotiations)) {
            negotiationRepository.save(negotiations);

            negotiations.forEach(notificationService::sendMatchEmailForSeller);
        }

    }

    public void approvedBySeller(String id) {
        negotiationRepository.findById(id).ifPresent(negotiation -> {
            negotiationApprovedBySellerRepository.save(NegotiationApprovedBySeller.of(negotiation));

            negotiationRepository.delete(negotiation);

            notificationService.sendMatchEmailForBuyer(negotiation);
        });
    }

    public void approvedByBuyer(String id) {
        negotiationApprovedBySellerRepository.findById(id).ifPresent(negotiation -> {
            negotiationClosedRepository.save(NegotiationClosed.of(negotiation, FINISHED));

            negotiationApprovedBySellerRepository.delete(negotiation);

            notificationService.sendDealEmail(negotiation);
        });
    }

    public void reprovedBySeller(String id) {
        negotiationRepository.findById(id).ifPresent(negotiation -> {
            negotiationClosedRepository.save(NegotiationClosed.of(negotiation));

            negotiationRepository.delete(negotiation);
        });
    }

    public void reprovedByBuyer(String id) {
        negotiationApprovedBySellerRepository.findById(id).ifPresent(negotiation -> {
            negotiationClosedRepository.save(NegotiationClosed.of(negotiation, NOT_APPROVED_BY_THE_BUYER));

            negotiationApprovedBySellerRepository.delete(negotiation);
        });
    }
}

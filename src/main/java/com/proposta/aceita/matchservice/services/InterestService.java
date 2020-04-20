package com.proposta.aceita.matchservice.services;

import com.proposta.aceita.matchservice.entities.Interest;
import com.proposta.aceita.matchservice.repositories.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InterestService {

    private final InterestRepository interestRepository;
    private final NegotiationService negotiationService;

    @Autowired
    public InterestService(InterestRepository interestRepository, NegotiationService negotiationService) {
        this.interestRepository = interestRepository;
        this.negotiationService = negotiationService;
    }

    public Optional<Interest> save(Interest interest) {

        negotiationService.findAsyncMatches(interest);

        return Optional.of(interestRepository.save(interest));
    }

    public void delete(Integer id) {

        interestRepository.deleteById(id);

        negotiationService.getNegotiationByInterestId(id).ifPresent(negotiationService::delete);
    }
}

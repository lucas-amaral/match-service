package com.proposta.aceita.matchservice.repositories;

import com.proposta.aceita.matchservice.entities.NegotiationApprovedBySeller;
import com.proposta.aceita.matchservice.entities.NegotiationClosed;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NegotiationApprovedBySellerRepository extends MongoRepository<NegotiationApprovedBySeller, String> {

}
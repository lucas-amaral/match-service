package com.proposta.aceita.matchservice.repositories;

import com.proposta.aceita.matchservice.entities.NegotiationClosed;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NegotiationClosedRepository extends MongoRepository<NegotiationClosed, String> {

}
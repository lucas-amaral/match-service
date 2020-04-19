package com.proposta.aceita.matchservice.repositories;

import com.proposta.aceita.matchservice.entities.Interest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterestRepository extends MongoRepository<Interest, Integer> {

}
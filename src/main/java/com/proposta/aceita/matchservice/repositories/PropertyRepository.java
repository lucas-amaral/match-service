package com.proposta.aceita.matchservice.repositories;

import com.proposta.aceita.matchservice.entities.Property;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropertyRepository extends MongoRepository<Property, Integer> {

}
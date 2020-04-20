package com.proposta.aceita.matchservice.repositories;

import com.proposta.aceita.matchservice.entities.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SaleRepository extends MongoRepository<Sale, Integer> {

}
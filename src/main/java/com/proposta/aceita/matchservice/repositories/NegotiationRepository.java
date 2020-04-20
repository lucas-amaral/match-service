package com.proposta.aceita.matchservice.repositories;

import com.proposta.aceita.matchservice.entities.Interest;
import com.proposta.aceita.matchservice.entities.Negotiation;
import com.proposta.aceita.matchservice.entities.Sale;
import com.proposta.aceita.matchservice.util.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.proposta.aceita.matchservice.entities.enums.BarterType.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class NegotiationRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public NegotiationRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void save(List<Negotiation> negotiations) {
        mongoTemplate.save(negotiations);
    }

    public void deleteById(String id) {
        mongoTemplate.findAndRemove(query(Criteria.where("_id").is(id)), Negotiation.class);
    }

    public void delete(List<Negotiation> negotiations) {
        mongoTemplate.remove(negotiations);
    }

    public Optional<Negotiation> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Negotiation.class));
    }

    public Optional<List<Negotiation>> findBySaleId(Integer saleId) {
        return Optional.of(mongoTemplate.find(query(Criteria.where("sales.id").is(saleId)), Negotiation.class));
    }

    public Optional<List<Negotiation>> findByInterestId(Integer interestId) {
        return Optional.of(mongoTemplate.find(query(Criteria.where("interest.id").is(interestId)), Negotiation.class));
    }

    public List<Interest> findInterestsBySale(Sale sale) {
        var query = new Query();

        query
            .addCriteria(where("neighborhoodIds").all(sale.getNeighborhoodId()))
            .addCriteria(where("types").all(sale.getType()))
            .addCriteria(where("value").gte(sale.getValue()))
            .addCriteria(where("dorms").lte(sale.getDorms()))
            .addCriteria(where("suites").lte(sale.getSuites()))
            .addCriteria(where("bathrooms").lte(sale.getBathrooms()))
            .addCriteria(where("garages").lte(sale.getGarages()));

        addFinancingCriteria(sale, query);
        addBartersCriteria(sale, query);
        addPoolCriteria(sale, query);
        addElevatorCriteria(sale, query);
        addBalconyCriteria(sale, query);
        addBarbecueGrillCriteria(sale, query);

        return mongoTemplate.find(query, Interest.class);
    }

    private void addFinancingCriteria(Sale sale, Query query) {
        if (!sale.getFinancing()) {
            query.addCriteria(where("financing").is(sale.getFinancing()));
        } else {
            query.addCriteria(where("financingValue").gte(sale.getFinancingValue()));
        }
    }

    private void addBartersCriteria(Sale sale, Query query) {
        if (sale.getBarterVehicle()) {
            query.addCriteria(where("barters")
                    .all(where("type").is(VEHICLE)
                            .andOperator(where("value").gte(sale.getBarterPropertyValue()))));
        }

        if (sale.getBarterProperty()) {
            query.addCriteria(where("barters")
                    .all(where("type").is(PROPERTY)
                            .andOperator(where("value").gte(sale.getBarterPropertyValue()))));
        }
    }

    private void addPoolCriteria(Sale sale, Query query) {
        if (!sale.getPool()) {
            query.addCriteria(where("pool").is(sale.getPool())
                    .orOperator(where("pool").exists(false)));
        }
    }

    private void addElevatorCriteria(Sale sale, Query query) {
        if (!sale.getElevator()) {
            query.addCriteria(where("elevator").is(sale.getElevator())
                    .orOperator(where("elevator").exists(false)));
        }
    }

    private void addBalconyCriteria(Sale sale, Query query) {
        if (!sale.getBalcony()) {
            query.addCriteria(where("balcony").is(sale.getBalcony())
                    .orOperator(where("balcony").exists(false)));
        }
    }

    private void addBarbecueGrillCriteria(Sale sale, Query query) {
        if (!sale.getBarbecueGrill()) {
            query.addCriteria(where("barbecueGrill").is(sale.getBarbecueGrill())
                    .orOperator(where("barbecueGrill").exists(false)));
        }
    }

    public List<Sale> findSalesByInterest(Interest interest) {
        var query = new Query();

        query
                .addCriteria(where("neighborhoodId").in(interest.getNeighborhoodIds()))
                .addCriteria(where("type").in(interest.getTypes()))
                .addCriteria(where("value").lte(interest.getValue()))
                .addCriteria(where("dorms").gte(interest.getDorms()))
                .addCriteria(where("suites").gte(interest.getSuites()))
                .addCriteria(where("bathrooms").gte(interest.getBathrooms()))
                .addCriteria(where("garages").gte(interest.getGarages()));

        addFinancingCriteria(interest, query);
        addBartersCriteria(interest, query);
        addPoolCriteria(interest, query);
        addElevatorCriteria(interest, query);
        addBalconyCriteria(interest, query);
        addBarbecueGrillCriteria(interest, query);

        return mongoTemplate.find(query, Sale.class);
    }

    private void addFinancingCriteria(Interest interest, Query query) {
        if (interest.getFinancing()) {
            query.addCriteria(where("financing").is(interest.getFinancing()))
                .addCriteria(where("financingValue").lte(interest.getFinancingValue()));
        }
    }

    private void addBartersCriteria(Interest interest, Query query) {
        if (!CheckUtils.listIsNullOrEmpty(interest.getBarters())) {
            interest.getBarters().forEach(barter -> {
                if (barter.getType().equals(VEHICLE)) {
                    query.addCriteria(where("barterVehicle").is(true))
                            .addCriteria(where("barterVehicleValue").lte(barter.getValue()));
                } else if (barter.getType().equals(PROPERTY)) {
                    query.addCriteria(where("barterProperty").is(true))
                            .addCriteria(where("barterPropertyValue").lte(barter.getValue()));
                }
            });
        }
    }

    private void addPoolCriteria(Interest interest, Query query) {
        if (interest.getPool() != null && interest.getPool()) {
            query.addCriteria(where("pool").is(interest.getPool()));
        }
    }

    private void addElevatorCriteria(Interest interest, Query query) {
        if (interest.getElevator() != null && interest.getElevator()) {
            query.addCriteria(where("elevator").is(interest.getElevator()));
        }
    }

    private void addBalconyCriteria(Interest interest, Query query) {
        if (interest.getBalcony() != null && interest.getBalcony()) {
            query.addCriteria(where("balcony").is(interest.getBalcony()));
        }
    }

    private void addBarbecueGrillCriteria(Interest interest, Query query) {
        if (interest.getBarbecueGrill() != null && interest.getBarbecueGrill()) {
            query.addCriteria(where("barbecueGrill").is(interest.getBarbecueGrill()));
        }
    }
}

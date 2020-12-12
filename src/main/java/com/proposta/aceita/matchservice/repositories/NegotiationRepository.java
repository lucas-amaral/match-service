package com.proposta.aceita.matchservice.repositories;

import com.proposta.aceita.matchservice.entities.Interest;
import com.proposta.aceita.matchservice.entities.Negotiation;
import com.proposta.aceita.matchservice.entities.NegotiationApprovedBySeller;
import com.proposta.aceita.matchservice.entities.Sale;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static com.proposta.aceita.matchservice.entities.enums.BarterType.PROPERTY;
import static com.proposta.aceita.matchservice.entities.enums.BarterType.VEHICLE;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class NegotiationRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public NegotiationRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Negotiation> save(List<Negotiation> negotiations) {
        return (List<Negotiation>) mongoTemplate.insertAll(negotiations);
    }

    public void deleteById(String id) {
        mongoTemplate.findAndRemove(query(Criteria.where("_id").is(new ObjectId(id))), Negotiation.class);
    }

    public void delete(Negotiation negotiation) {
        mongoTemplate.remove(negotiation);
    }

    public Optional<Negotiation> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(new ObjectId(id), Negotiation.class));
    }

    public List<Negotiation> findBySaleId(Integer saleId) {
        return mongoTemplate.find(query(Criteria.where("sale.id").is(saleId)), Negotiation.class);
    }

    public List<NegotiationApprovedBySeller> findApprovedBySaleId(Integer saleId) {
        return mongoTemplate.find(query(Criteria.where("sale.id").is(saleId)), NegotiationApprovedBySeller.class);
    }

    public List<Negotiation> findByInterestId(Integer interestId) {
        return mongoTemplate.find(query(Criteria.where("interest.id").is(interestId)), Negotiation.class);
    }

    public List<NegotiationApprovedBySeller> findApprovedByInterestId(Integer interestId) {
        return mongoTemplate.find(query(Criteria.where("interest.id").is(interestId)), NegotiationApprovedBySeller.class);
    }

    public List<Interest> findInterestsBySale(Sale sale) {
        var criteria = new Criteria()
                .and("neighborhoodIds").all(List.of(sale.getNeighborhoodId()))
                .and("types").all(List.of(sale.getType()))
                .and("value").gte(sale.getValue())
                .and("dorms").lte(sale.getDorms())
                .and("suites").lte(sale.getSuites())
                .and("bathrooms").lte(sale.getBathrooms())
                .and("garages").lte(sale.getGarages())
                .andOperator(addFinancingCriteria(sale),
                        addBartersVehicleCriteria(sale),
                        addBartersPropertyCriteria(sale));

        addPoolCriteria(criteria, sale);
        addElevatorCriteria(criteria, sale);
        addBalconyCriteria(criteria, sale);
        addBarbecueGrillCriteria(criteria, sale);

        return mongoTemplate.find(new Query().addCriteria(criteria), Interest.class);
    }

    private Criteria addFinancingCriteria(Sale sale) {
        if (!sale.getFinancing()) {
            return where("financing").is(false);
        } else {
            var criteriaValue = where("financingValue").lte(sale.getFinancingValue());
            var criteriaNotExist = where("financingValue").exists(false);
            return new Criteria().orOperator(criteriaValue, criteriaNotExist);
        }
    }

    private Criteria addBartersVehicleCriteria(Sale sale) {
        if (sale.getBarterVehicle()) {
            return new Criteria().orOperator(where("barters").exists(false),
                    where("barters").elemMatch(where("type").is(VEHICLE).and("value").lte(sale.getBarterVehicleValue())));
        }

        return new Criteria();
    }

    private Criteria addBartersPropertyCriteria(Sale sale) {
        if (sale.getBarterProperty()) {
            return new Criteria().orOperator(where("barters").exists(false),
                    where("barters").elemMatch(where("type").is(PROPERTY).and("value").lte(sale.getBarterPropertyValue())));
        }

        return new Criteria();
    }

    private void addPoolCriteria(Criteria criteria, Sale sale) {
        if (!sale.getPool()) {
            criteria.and("pool").ne(true);
        }
    }

    private void addElevatorCriteria(Criteria criteria, Sale sale) {
        if (!sale.getElevator()) {
            criteria.and("elevator").ne(true);
        }
    }

    private void addBalconyCriteria(Criteria criteria, Sale sale) {
        if (!sale.getBalcony()) {
            criteria.and("balcony").ne(true);
        }
    }

    private void addBarbecueGrillCriteria(Criteria criteria, Sale sale) {
        if (!sale.getBarbecueGrill()) {
            criteria.and("barbecueGrill").ne(true);
        }
    }

    public List<Sale> findSalesByInterest(Interest interest) {
        var criteria = new Criteria()
                .and("neighborhoodId").in(interest.getNeighborhoodIds())
                .and("type").in(interest.getTypes())
                .and("value").lte(interest.getValue())
                .and("dorms").gte(interest.getDorms())
                .and("suites").gte(interest.getSuites())
                .and("bathrooms").gte(interest.getBathrooms())
                .and("garages").gte(interest.getGarages());

        addFinancingCriteria(criteria, interest);
        addBartersCriteria(criteria, interest);
        addPoolCriteria(criteria, interest);
        addElevatorCriteria(interest, criteria);
        addBalconyCriteria(interest, criteria);
        addBarbecueGrillCriteria(interest, criteria);

        return mongoTemplate.find(new Query().addCriteria(criteria), Sale.class);
    }

    private void addFinancingCriteria(Criteria criteria, Interest interest) {
        if (interest.getFinancing()) {
            criteria
                    .and("financing").is(interest.getFinancing())
                    .and("financingValue").gte(interest.getFinancingValue());
        }
    }

    private void addBartersCriteria(Criteria criteria, Interest interest) {
        if (!CollectionUtils.isEmpty(interest.getBarters())) {
            interest.getBarters().forEach(barter -> {
                if (barter.getType().equals(VEHICLE)) {
                    criteria
                            .and("barterVehicle").is(true)
                            .and("barterVehicleValue").gte(barter.getValue());
                } else if (barter.getType().equals(PROPERTY)) {
                    criteria
                            .and("barterProperty").is(true)
                            .and("barterPropertyValue").gte(barter.getValue());
                }
            });
        }
    }

    private void addPoolCriteria(Criteria criteria, Interest interest) {
        if (interest.getPool() != null && interest.getPool()) {
            criteria.and("pool").is(interest.getPool());
        }
    }

    private void addElevatorCriteria(Interest interest, Criteria criteria) {
        if (interest.getElevator() != null && interest.getElevator()) {
            criteria.and("elevator").is(interest.getElevator());
        }
    }

    private void addBalconyCriteria(Interest interest, Criteria criteria) {
        if (interest.getBalcony() != null && interest.getBalcony()) {
            criteria.and("balcony").is(interest.getBalcony());
        }
    }

    private void addBarbecueGrillCriteria(Interest interest, Criteria criteria) {
        if (interest.getBarbecueGrill() != null && interest.getBarbecueGrill()) {
            criteria.and("barbecueGrill").is(interest.getBarbecueGrill());
        }
    }
}

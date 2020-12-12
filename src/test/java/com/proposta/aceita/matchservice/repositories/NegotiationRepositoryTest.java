package com.proposta.aceita.matchservice.repositories;

import com.proposta.aceita.matchservice.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.proposta.aceita.matchservice.entities.enums.BarterType.PROPERTY;
import static com.proposta.aceita.matchservice.entities.enums.BarterType.VEHICLE;
import static com.proposta.aceita.matchservice.entities.enums.PropertyType.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class NegotiationRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private SaleRepository saleRepository;

    private NegotiationRepository negotiationRepository;

    @BeforeEach
    public void setup() {
        negotiationRepository = new NegotiationRepository(mongoTemplate);
    }

    public Negotiation createNegotiation() {
        var barter = new Barter(1, VEHICLE, 34.32);
        var interest = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var sale = new Sale(144, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.0, false, null);

        var negotiation = new Negotiation("5e9e2e115c191353f0a60c37", interest, sale, LocalDateTime.of(2020, 3, 4, 10, 15));

        return mongoTemplate.save(negotiation);
    }

    public NegotiationApprovedBySeller createApprovedNegotiation() {
        var barter = new Barter(1, VEHICLE, 34.32);
        var interest = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var sale = new Sale(144, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.0, false, null);

        var negotiation = new NegotiationApprovedBySeller("5e9e2e115c191353f0a60c37", interest, sale, LocalDateTime.of(2020, 3, 4, 10, 15));

        return mongoTemplate.save(negotiation);
    }

    @Test
    public void save() {

        var barter = new Barter(1, VEHICLE, 34.32);
        var interest = new Interest(127, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var sale = new Sale(574, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.0, false, null);

        var negotiation = new Negotiation(null, interest, sale, LocalDateTime.of(2020, 3, 4, 10, 15));

        assertThat(negotiationRepository.save(List.of(negotiation))).isNotEmpty().containsOnly(negotiation);
    }

    @Test
    public void deleteById() {

        var negotiation = createNegotiation();

        var id = negotiation.getId();

        negotiationRepository.deleteById(id);

        assertThat(negotiationRepository.findById(id)).isEmpty();
    }

    @Test
    public void delete() {

        var negotiation = createNegotiation();

        negotiationRepository.delete(negotiation);

        assertThat(negotiationRepository.findById(negotiation.getId())).isEmpty();
    }

    @Test
    public void findBySaleId() {

        var saleId = 144;

        var negotiation = createNegotiation();

        assertThat(negotiationRepository.findBySaleId(saleId)).isEqualTo(List.of(negotiation));
    }

    @Test
    public void findApprovedBySaleId() {

        var saleId = 144;

        var negotiation = createApprovedNegotiation();

        assertThat(negotiationRepository.findApprovedBySaleId(saleId)).isEqualTo(List.of(negotiation));
    }

    @Test
    public void findByInterestId() {

        var interestId = 234;

        var negotiation = createNegotiation();

        assertThat(negotiationRepository.findByInterestId(interestId)).isEqualTo(List.of(negotiation)   );
    }

    @Test
    public void findApprovedByInterestId() {

        var interestId = 234;

        var negotiation = createApprovedNegotiation();

        assertThat(negotiationRepository.findApprovedByInterestId(interestId)).isEqualTo(List.of(negotiation)   );
    }

    @Test
    public void findInterestsBySale() {
        var barterVehicle = new Barter(1, VEHICLE, 90.99);
        var barterProperty = new Barter(2, PROPERTY, 201.40);
        var barterVehicleHighestPrice = new Barter(1, VEHICLE, 320.01);

        var interestMatch = new Interest(234, 413.65, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, false, false, false, List.of(barterVehicle));
        var interestInvalidBarter = new Interest(235, 495.50, false, null, List.of(APARTMENT), List.of(4,3,5), 2, 0, 2, 1, false, false, false, false, List.of(barterProperty));
        var interestHighestPriceBarter = new Interest(236, 413.65, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, false, false, false, List.of(barterVehicleHighestPrice));
        var interestFinancingValue = new Interest(237, 495.50, true, 310.10, List.of(HOUSE, APARTMENT), List.of(3), 2, 1, 3, 1, false, false, false, false, List.of(barterVehicle));
        var interestLowerPrice = new Interest(238, 410.98, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, false, false, false, List.of(barterVehicle));
        var interestOthersPropertyType = new Interest(239, 413.65, false, null, List.of(HOUSE, COMERCIAL), List.of(1,3), 3, 1, 3, 2, false, false, false, false, List.of(barterVehicle));
        var interestOthersNeighborhoods = new Interest(240, 413.65, false, null, List.of(APARTMENT), List.of(1,7,9), 3, 1, 3, 2, false, false, false, false, List.of(barterVehicle));
        var interestMoreDorms = new Interest(241, 413.65, false, null, List.of(APARTMENT), List.of(1,3), 4, 1, 3, 2, false, false, false, false, List.of(barterVehicle));
        var interestMoreSuites = new Interest(242, 413.65, false, null, List.of(APARTMENT), List.of(1,3), 3, 3, 3, 2, false, false, false, false, List.of(barterVehicle));
        var interestMoreBathrooms = new Interest(243, 413.65, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 4, 2, false, false, false, false, List.of(barterVehicle));
        var interestMoreGarages = new Interest(244, 413.65, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 3, false, false, false, false, List.of(barterVehicle));
        var interestWithPool = new Interest(245, 413.65, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, true, false, false, false, List.of(barterVehicle));
        var interestWithBalcony = new Interest(246, 413.65, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, false, false, List.of(barterVehicle));
        var interestWithElevator = new Interest(247, 413.65, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, false, true, false, List.of(barterVehicle));
        var interestWithBarbecueGrill = new Interest(248, 413.65, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, false, false, true, List.of(barterVehicle));

        interestRepository.saveAll(List.of(interestMatch,
                interestInvalidBarter,
                interestHighestPriceBarter,
                interestFinancingValue,
                interestLowerPrice,
                interestOthersPropertyType,
                interestOthersNeighborhoods,
                interestMoreDorms,
                interestMoreSuites,
                interestMoreBathrooms,
                interestMoreGarages,
                interestWithPool,
                interestWithBalcony,
                interestWithElevator,
                interestWithBarbecueGrill));

        var sale = new Sale(144, 32, 3, APARTMENT, 3, 2, 3, 2, false, false, false, false, 410.99, true, 300.00, true, 154.99, false, null);

        assertThat(negotiationRepository.findInterestsBySale(sale)).containsOnly(interestMatch);
    }

    @Test
    public void findSalesByInterest() {

        var saleMatch = new Sale(234, 156, 3, APARTMENT, 3, 1, 3, 2, true, true, true, true, 400.00, true, 320.00, true, 114.99, false, null);
        var saleInvalidBarter = new Sale(235, 8145, 3, APARTMENT, 3, 1, 3, 2, true, true, true, true, 350.00, true, 320.00, false, null, true,  200.00);
        var saleLowerPriceBarter = new Sale(236, 48, 3, APARTMENT, 3, 1, 3, 2, true, true, true, true, 400.50, true, 320.00, true, 99.00, false, null);
        var saleFinancingValue = new Sale(237, 925, 3, APARTMENT, 2, 1, 2, 1, true, true, true, true, 500.00, true, 300.00, true, 114.99, false, null);
        var saleHighestPrice = new Sale(238, 123, 3, APARTMENT, 3, 1, 3, 2, true, true, true, true, 400.01, true, 320.00, true, 114.99, false, null);
        var saleOthersPropertyType = new Sale(239, 945, 3, HOUSE, 3, 1, 3, 2, true, true, true, true, 400.00, true, 320.00, true, 114.99, false, null);
        var saleOthersNeighborhoods = new Sale(240, 851, 7, APARTMENT, 3, 1, 3, 2, true, true, true, true, 400.00, true, 320.00, true, 114.99, false, null);
        var saleLessDorms = new Sale(241, 815, 3, APARTMENT, 2, 1, 3, 2, true, true, true, true, 400.00, true, 320.00, true, 114.99, false, null);
        var saleLessSuites = new Sale(242, 988, 3, APARTMENT, 3, 0, 3, 2, true, true, true, true, 400.00, true, 320.00, true, 114.99, false, null);
        var saleLessBathrooms = new Sale(243, 518, 3, APARTMENT, 3, 1, 2, 2, true, true, true, true, 400.00, true, 320.00, true, 114.99, false, null);
        var saleLessGarages = new Sale(244, 218, 3, APARTMENT, 3, 1, 3, 1, true, true, true, true, 400.00, true, 320.00, true, 114.99, false, null);
        var saleWithoutPool = new Sale(245, 8, 3, APARTMENT, 3, 1, 3, 2, false, true, true, true, 400.00, true, 320.00, true, 114.99, false, null);
        var saleWithoutBalcony = new Sale(246, 15, 3, APARTMENT, 3, 1, 3, 2, true,  false, true, true, 400.00, true, 320.00,true, 114.99, false, null);
        var saleWithoutElevator = new Sale(247, 8941, 3, APARTMENT, 3, 1, 3, 2, true, true, false, true, 400.00, true, 320.00,true, 114.99, false, null);
        var saleWithoutBarbecueGrill = new Sale(248, 9, 3, APARTMENT, 3, 1, 3, 2, true, true, true, false, 400.00, true, 320.00,true, 114.99, false, null);

        saleRepository.saveAll(List.of(saleMatch,
                saleInvalidBarter,
                saleLowerPriceBarter,
                saleFinancingValue,
                saleHighestPrice,
                saleOthersPropertyType,
                saleOthersNeighborhoods,
                saleLessDorms,
                saleLessSuites,
                saleLessBathrooms,
                saleLessGarages,
                saleWithoutPool,
                saleWithoutBalcony,
                saleWithoutElevator,
                saleWithoutBarbecueGrill));

        var barter = new Barter(1, VEHICLE, 114.99);
        var interest = new Interest(144, 400.00, true, 320.00, List.of(COMERCIAL, APARTMENT), List.of(1,2,3), 3, 1, 3, 2, true, true, true, true, List.of(barter));

        assertThat(negotiationRepository.findSalesByInterest(interest)).containsOnly(saleMatch);
    }

}

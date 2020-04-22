package com.proposta.aceita.matchservice.services;

import com.proposta.aceita.matchservice.entities.Barter;
import com.proposta.aceita.matchservice.entities.Interest;
import com.proposta.aceita.matchservice.entities.Negotiation;
import com.proposta.aceita.matchservice.entities.Sale;
import com.proposta.aceita.matchservice.repositories.InterestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static com.proposta.aceita.matchservice.entities.enums.BarterType.VEHICLE;
import static com.proposta.aceita.matchservice.entities.enums.PropertyType.APARTMENT;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class InterestServiceTest {

    @MockBean
    private InterestRepository interestRepository;

    @MockBean
    private NegotiationService negotiationService;

    private InterestService interestService;

    @BeforeEach
    public void setup() {
        interestService = new InterestService(interestRepository, negotiationService);
    }

    @Test
    public void save() {

        var barter = new Barter(VEHICLE, 34.32);
        var interest = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));

        when(interestRepository.save(interest)).thenReturn(interest);

        interestService.save(interest);

        verify(interestRepository).save(interest);

        verify(negotiationService).findAsyncMatches(interest);
    }

    @Test
    public void delete() {

        var id = 32;

        var barter = new Barter(VEHICLE, 34.32);
        var interest = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var sale = new Sale(234, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.00, false, null);

        var negotiation1 = new Negotiation("2324sdd022343", interest, sale, LocalDateTime.of(2020, 3, 4, 10, 15));
        var negotiation2 = new Negotiation("23m2jjkssaio2", interest, sale, LocalDateTime.of(2020, 2, 24, 4, 46));

        when(negotiationService.getNegotiationByInterestId(id)).thenReturn(List.of(negotiation1, negotiation2));

        interestService.delete(id);

        verify(interestRepository).deleteById(id);

        verify(negotiationService).delete(negotiation1);
        verify(negotiationService).delete(negotiation2);
    }

}

package com.proposta.aceita.matchservice.services;

import com.proposta.aceita.matchservice.entities.*;
import com.proposta.aceita.matchservice.repositories.NegotiationApprovedBySellerRepository;
import com.proposta.aceita.matchservice.repositories.NegotiationClosedRepository;
import com.proposta.aceita.matchservice.repositories.NegotiationRepository;
import com.proposta.aceita.matchservice.services.integrations.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.proposta.aceita.matchservice.entities.enums.BarterType.VEHICLE;
import static com.proposta.aceita.matchservice.entities.enums.NegotiationStatus.*;
import static com.proposta.aceita.matchservice.entities.enums.PropertyType.APARTMENT;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NegotiationServiceTest {

    @MockBean
    private NegotiationRepository negotiationRepository;

    @MockBean
    private NegotiationApprovedBySellerRepository negotiationApprovedBySellerRepository;

    @MockBean
    private NegotiationClosedRepository negotiationClosedRepository;

    @MockBean
    private NotificationService notificationService;

    private NegotiationService negotiationService;

    @BeforeEach
    public void setup() {
        negotiationService = new NegotiationService(negotiationRepository, negotiationApprovedBySellerRepository, negotiationClosedRepository, notificationService);
    }

    @Test
    public void getNegotiationBySaleId() {
        var saleId = 61;

        negotiationService.getNegotiationBySaleId(saleId);

        verify(negotiationRepository).findBySaleId(saleId);
    }

    @Test
    public void getNegotiationByInterestId() {
        var interestId = 61;

        negotiationService.getNegotiationByInterestId(interestId);

        verify(negotiationRepository).findByInterestId(interestId);
    }

    @Test
    public void deleteById() {
        var id = "anjass29kxkjansajkns";

        negotiationService.delete(id);

        verify(negotiationRepository).deleteById(id);
    }

    @Test
    public void delete() {
        var barter = new Barter(1, VEHICLE, 34.32);
        var interest = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var sale = new Sale(234, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.00, false, null);

        var negotiation = new Negotiation("2324sdd022343", interest, sale, LocalDateTime.of(2020, 3, 4, 10, 15));

        negotiationService.delete(negotiation);

        verify(negotiationRepository).delete(negotiation);
    }

    @Test
    public void findMatchesBySale() {
        var barter = new Barter(1, VEHICLE, 34.32);
        var interest234 = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var interest235 = new Interest(235, 1323.23, false, null, List.of(APARTMENT), List.of(3), 2, 0, 2, 1, false, true, true, true, List.of(barter));
        var sale = new Sale(144, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.00, false, null);

        when(negotiationRepository.findInterestsBySale(sale)).thenReturn(List.of(interest234, interest235));

        negotiationService.findMatches(sale);

        verify(negotiationRepository).save(List.of(
                new Negotiation(null, interest234, sale, LocalDateTime.now()),
                new Negotiation(null, interest235, sale, LocalDateTime.now())));

        verify(notificationService).sendMatchEmailForSeller(new Negotiation(null, interest234, sale, LocalDateTime.now()));
        verify(notificationService).sendMatchEmailForSeller(new Negotiation(null, interest235, sale, LocalDateTime.now()));
    }

    @Test
    public void dontFindMatchesBySale() {
        var sale = new Sale(144, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.00, false, null);

        when(negotiationRepository.findInterestsBySale(sale)).thenReturn(Collections.emptyList());

        negotiationService.findMatches(sale);

        verify(negotiationRepository).findInterestsBySale(sale);

        verifyNoMoreInteractions(negotiationRepository);
        verifyNoInteractions(notificationService);
    }

    @Test
    public void findMatchesByInterest() {
        var barter = new Barter(1, VEHICLE, 34.32);
        var interest = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var sale144 = new Sale(144, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.00, false, null);
        var sale146 = new Sale(146, 235, 2, APARTMENT, 2, 1, 1, 1, false, true, false, true, 23554.26, true, 214.55, true, 100.00, false, null);

        when(negotiationRepository.findSalesByInterest(interest)).thenReturn(List.of(sale144, sale146));

        negotiationService.findMatches(interest);

        verify(negotiationRepository).save(List.of(
                new Negotiation(null, interest, sale144, LocalDateTime.now()),
                new Negotiation(null, interest, sale146, LocalDateTime.now())));

        verify(notificationService).sendMatchEmailForSeller(new Negotiation(null, interest, sale144, LocalDateTime.now()));
        verify(notificationService).sendMatchEmailForSeller(new Negotiation(null, interest, sale146, LocalDateTime.now()));
    }

    @Test
    public void dontFindMatchesByInterest() {
        var barter = new Barter(1, VEHICLE, 34.32);
        var interest = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));

        when(negotiationRepository.findSalesByInterest(interest)).thenReturn(Collections.emptyList());

        negotiationService.findMatches(interest);

        verify(negotiationRepository).findSalesByInterest(interest);

        verifyNoMoreInteractions(negotiationRepository);
        verifyNoInteractions(notificationService);
    }

    @Test
    public void approvedBySeller() {
        var id = "2324sdd022343";

        var barter = new Barter(1, VEHICLE, 34.32);
        var interest = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var sale = new Sale(144, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.00, false, null);

        var negotiation = new Negotiation(id, interest, sale, LocalDateTime.of(2020, 3, 4, 10, 15));

        when(negotiationRepository.findById(id)).thenReturn(Optional.of(negotiation));

        negotiationService.approvedBySeller(id);

        verify(negotiationApprovedBySellerRepository).save(new NegotiationApprovedBySeller(id, interest, sale, LocalDateTime.now()));
        verify(notificationService).sendMatchEmailForBuyer(new Negotiation(id, interest, sale, LocalDateTime.now()));
        verify(negotiationRepository).delete(negotiation);
    }

    @Test
    public void approvedBySellerWithoutNegotiation() {
        var id = "2324sdd022343";

        when(negotiationRepository.findById(id)).thenReturn(Optional.empty());

        negotiationService.approvedBySeller(id);

        verify(negotiationRepository).findById(id);
        verifyNoInteractions(negotiationApprovedBySellerRepository, notificationService);
        verifyNoMoreInteractions(negotiationRepository);
    }

    @Test
    public void approvedByBuyer() {
        var id = "2324sdd022343";

        var barter = new Barter(1, VEHICLE, 34.32);
        var interest = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var sale = new Sale(144, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.00, false, null);

        var negotiation = new NegotiationApprovedBySeller(id, interest, sale, LocalDateTime.of(2020, 3, 4, 10, 15));

        when(negotiationApprovedBySellerRepository.findById(id)).thenReturn(Optional.of(negotiation));

        negotiationService.approvedByBuyer(id);

        verify(negotiationClosedRepository).save(new NegotiationClosed(id, interest, sale, FINISHED, LocalDateTime.now()));
        verify(notificationService).sendDealEmail(negotiation);
        verify(negotiationApprovedBySellerRepository).delete(negotiation);
    }

    @Test
    public void approvedByBuyerWithoutNegotiation() {
        var id = "2324sdd022343";

        when(negotiationApprovedBySellerRepository.findById(id)).thenReturn(Optional.empty());

        negotiationService.approvedByBuyer(id);

        verify(negotiationApprovedBySellerRepository).findById(id);
        verifyNoInteractions(negotiationClosedRepository, notificationService);
        verifyNoMoreInteractions(negotiationApprovedBySellerRepository);
    }

    @Test
    public void reprovedBySeller() {
        var id = "2324sdd022343";

        var barter = new Barter(1, VEHICLE, 34.32);
        var interest = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var sale = new Sale(144, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.00, false, null);

        var negotiation = new Negotiation(id, interest, sale, LocalDateTime.of(2020, 3, 4, 10, 15));

        when(negotiationRepository.findById(id)).thenReturn(Optional.of(negotiation));

        negotiationService.reprovedBySeller(id);

        verify(negotiationClosedRepository).save(new NegotiationClosed(id, interest, sale, NOT_APPROVED_BY_THE_SELLER, LocalDateTime.now()));
        verify(negotiationRepository).delete(negotiation);
    }

    @Test
    public void reprovedBySellerWithoutNegotiation() {
        var id = "2324sdd022343";

        when(negotiationRepository.findById(id)).thenReturn(Optional.empty());

        negotiationService.reprovedBySeller(id);

        verify(negotiationRepository).findById(id);
        verifyNoInteractions(negotiationApprovedBySellerRepository);
        verifyNoMoreInteractions(negotiationRepository);
    }

    @Test
    public void reprovedByBuyer() {
        var id = "2324sdd022343";

        var barter = new Barter(1, VEHICLE, 34.32);
        var interest = new Interest(234, 1213.23, false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var sale = new Sale(144, 32, 3, APARTMENT, 3, 2, 2, 1, true, true, true, false, 34554.26, true, 214.55, true, 100.00, false, null);

        var negotiation = new NegotiationApprovedBySeller(id, interest, sale, LocalDateTime.of(2020, 3, 4, 10, 15));

        when(negotiationApprovedBySellerRepository.findById(id)).thenReturn(Optional.of(negotiation));

        negotiationService.reprovedByBuyer(id);

        verify(negotiationClosedRepository).save(new NegotiationClosed(id, interest, sale, NOT_APPROVED_BY_THE_BUYER, LocalDateTime.now()));
        verify(negotiationApprovedBySellerRepository).delete(negotiation);
    }

    @Test
    public void reprovedByBuyerWithoutNegotiation() {
        var id = "2324sdd022343";

        when(negotiationApprovedBySellerRepository.findById(id)).thenReturn(Optional.empty());

        negotiationService.reprovedByBuyer(id);

        verify(negotiationApprovedBySellerRepository).findById(id);
        verifyNoInteractions(negotiationClosedRepository);
        verifyNoMoreInteractions(negotiationApprovedBySellerRepository);
    }

}

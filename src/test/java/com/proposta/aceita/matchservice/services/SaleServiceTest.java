package com.proposta.aceita.matchservice.services;

import com.proposta.aceita.matchservice.entities.Barter;
import com.proposta.aceita.matchservice.entities.Interest;
import com.proposta.aceita.matchservice.entities.Negotiation;
import com.proposta.aceita.matchservice.entities.Sale;
import com.proposta.aceita.matchservice.repositories.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.proposta.aceita.matchservice.entities.enums.BarterType.VEHICLE;
import static com.proposta.aceita.matchservice.entities.enums.PropertyType.APARTMENT;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SaleServiceTest {

    @MockBean
    private SaleRepository saleRepository;

    @MockBean
    private NegotiationService negotiationService;

    private SaleService saleService;

    @BeforeEach
    public void setup() {
        saleService = new SaleService(saleRepository, negotiationService);
    }

    @Test
    public void save() {

        var sale = new Sale(234, 32, 3, APARTMENT, 3, 2, 2, true, true, true, false, 1, BigDecimal.valueOf(34554.26), true, BigDecimal.valueOf(214.55), true, BigDecimal.valueOf(100), false, null);

        when(saleRepository.save(sale)).thenReturn(sale);

        saleService.save(sale);

        verify(saleRepository).save(sale);

        verify(negotiationService).findAsyncMatches(sale);
    }

    @Test
    public void delete() {

        var id = 32;

        var barter = new Barter(VEHICLE, BigDecimal.valueOf(3432, 2));
        var interest = new Interest(234, BigDecimal.valueOf(121323,2), false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, 2, false, true, true, true, List.of(barter));
        var sale = new Sale(234, 32, 3, APARTMENT, 3, 2, 2, true, true, true, false, 1, BigDecimal.valueOf(34554.26), true, BigDecimal.valueOf(214.55), true, BigDecimal.valueOf(100), false, null);

        var negotiation1 = new Negotiation("2324sdd022343", interest, sale, LocalDateTime.of(2020, 3, 4, 10, 15));
        var negotiation2 = new Negotiation("23m2jjkssaio2", interest, sale, LocalDateTime.of(2020, 2, 24, 4, 46));

        when(negotiationService.getNegotiationBySaleId(id)).thenReturn(Optional.of(List.of(negotiation1, negotiation2)));

        saleService.delete(id);

        verify(saleRepository).deleteById(id);

        verify(negotiationService).delete(List.of(negotiation1, negotiation2));
    }

}

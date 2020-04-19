package com.proposta.aceita.matchservice.services;

import com.proposta.aceita.matchservice.entities.Barter;
import com.proposta.aceita.matchservice.entities.Interest;
import com.proposta.aceita.matchservice.repositories.InterestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

import static com.proposta.aceita.matchservice.entities.enums.BarterType.CAR;
import static com.proposta.aceita.matchservice.entities.enums.PropertyType.APARTMENT;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class InterestServiceTest {

    @MockBean
    private InterestRepository interestRepository;

    private InterestService interestService;

    @BeforeEach
    public void setup() {
        interestService = new InterestService(interestRepository);
    }

    @Test
    public void save() {

        var barter = new Barter(CAR, BigDecimal.valueOf(3432, 2));
        var interest = new Interest(234, BigDecimal.valueOf(121323,2), false, null, List.of(APARTMENT), List.of(1,3), 3, 1, 3, false, true, true, true, List.of(barter));

        when(interestRepository.save(interest)).thenReturn(interest);

        interestService.save(interest);

        verify(interestRepository).save(interest);
    }

    @Test
    public void delete() {

        var id = 32;

        interestService.delete(id);

        verify(interestRepository).deleteById(id);
    }

}

package com.proposta.aceita.matchservice.services;

import com.proposta.aceita.matchservice.entities.Barter;
import com.proposta.aceita.matchservice.entities.Property;
import com.proposta.aceita.matchservice.repositories.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.proposta.aceita.matchservice.entities.enums.BarterType.CAR;
import static com.proposta.aceita.matchservice.entities.enums.PropertyType.APARTMENT;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PropertyServiceTest {

    @MockBean
    private PropertyRepository propertyRepository;

    private PropertyService propertyService;

    @BeforeEach
    public void setup() {
        propertyService = new PropertyService(propertyRepository);
    }

    @Test
    public void save() {

        var property = new Property(234, 3, APARTMENT, 3, 2, 2, true, true, true, false, 1, BigDecimal.valueOf(34554.26), true, BigDecimal.valueOf(214.55), true, BigDecimal.valueOf(100), false, null);

        when(propertyRepository.save(property)).thenReturn(property);

        propertyService.save(property);

        verify(propertyRepository).save(property);
    }

    @Test
    public void delete() {

        var id = 32;

        propertyService.delete(id);

        verify(propertyRepository).deleteById(id);
    }

}

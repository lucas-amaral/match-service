package com.proposta.aceita.matchservice.services;

import com.proposta.aceita.matchservice.entities.Property;
import com.proposta.aceita.matchservice.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public Optional<Property> save(Property property) {
        return Optional.of(propertyRepository.save(property));
    }

    public void delete(Integer id) {
        propertyRepository.deleteById(id);
    }
}

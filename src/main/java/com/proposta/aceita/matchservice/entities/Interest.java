package com.proposta.aceita.matchservice.entities;

import com.proposta.aceita.matchservice.entities.enums.PropertyType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Document("interests")
public class Interest {
    @Id
    private final Integer id;
    private final Double value;
    private final Boolean financing;
    private final Double financingValue;
    private final List<PropertyType> types;
    private final List<Integer> neighborhoodIds;
    private final Integer dorms;
    private final Integer suites;
    private final Integer bathrooms;
    private final Integer garages;
    private final Boolean pool;
    private final Boolean balcony;
    private final Boolean elevator;
    private final Boolean barbecueGrill;
    private final List<Barter> barters;

    public Interest(Integer id, Double value, Boolean financing, Double financingValue, List<PropertyType> types, List<Integer> neighborhoodIds, Integer dorms, Integer suites, Integer bathrooms, Integer garages, Boolean pool, Boolean balcony, Boolean elevator, Boolean barbecueGrill, List<Barter> barters) {
        this.id = id;
        this.value = value;
        this.financing = financing;
        this.financingValue = financingValue;
        this.types = types;
        this.neighborhoodIds = neighborhoodIds;
        this.dorms = (dorms == null) ? 0 : dorms;
        this.suites = (suites == null) ? 0 : suites;
        this.bathrooms = (bathrooms == null) ? 0 : bathrooms;
        this.garages = (garages == null) ? 0 : garages;
        this.pool = pool;
        this.balcony = balcony;
        this.elevator = elevator;
        this.barbecueGrill = barbecueGrill;
        this.barters = barters;
    }

    public Integer getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public Boolean getFinancing() {
        return financing;
    }

    public Double getFinancingValue() {
        return financingValue;
    }

    public List<PropertyType> getTypes() {
        return types;
    }

    public List<Integer> getNeighborhoodIds() {
        return neighborhoodIds;
    }

    public Integer getDorms() {
        return dorms;
    }

    public Integer getSuites() {
        return suites;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public Integer getGarages() {
        return garages;
    }

    public Boolean getPool() {
        return pool;
    }

    public Boolean getBalcony() {
        return balcony;
    }

    public Boolean getElevator() {
        return elevator;
    }

    public Boolean getBarbecueGrill() {
        return barbecueGrill;
    }

    public List<Barter> getBarters() {
        return barters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interest interest = (Interest) o;
        return Objects.equals(id, interest.id) &&
                Objects.equals(value, interest.value) &&
                Objects.equals(financing, interest.financing) &&
                Objects.equals(financingValue, interest.financingValue) &&
                Objects.equals(types, interest.types) &&
                Objects.equals(neighborhoodIds, interest.neighborhoodIds) &&
                Objects.equals(dorms, interest.dorms) &&
                Objects.equals(suites, interest.suites) &&
                Objects.equals(bathrooms, interest.bathrooms) &&
                Objects.equals(garages, interest.garages) &&
                Objects.equals(pool, interest.pool) &&
                Objects.equals(balcony, interest.balcony) &&
                Objects.equals(elevator, interest.elevator) &&
                Objects.equals(barbecueGrill, interest.barbecueGrill) &&
                Objects.equals(barters, interest.barters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, financing, financingValue, types, neighborhoodIds, dorms, suites, bathrooms, garages, pool, balcony, elevator, barbecueGrill, barters);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Interest.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("value=" + value)
                .add("financing=" + financing)
                .add("financingValue=" + financingValue)
                .add("types=" + types)
                .add("neighborhoodIds=" + neighborhoodIds)
                .add("dorms=" + dorms)
                .add("suites=" + suites)
                .add("bathrooms=" + bathrooms)
                .add("garages=" + garages)
                .add("pool=" + pool)
                .add("balcony=" + balcony)
                .add("elevator=" + elevator)
                .add("barbecueGrill=" + barbecueGrill)
                .add("barters=" + barters)
                .toString();
    }
}


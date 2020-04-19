package com.proposta.aceita.matchservice.entities;

import com.proposta.aceita.matchservice.entities.enums.PropertyType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

@Document("properties")
public class Property {
    @Id
    private final Integer id;
    private final Integer neighborhoodId;
    private final PropertyType type;
    private final Integer dorms;
    private final Integer suites;
    private final Integer bathrooms;
    private final Boolean pool;
    private final Boolean balcony;
    private final Boolean elevator;
    private final Boolean barbecueGrill;
    private final Integer garages;
    private final BigDecimal value;
    private final Boolean financing;
    private final BigDecimal financingValue;
    private final Boolean barterVehicle;
    private final BigDecimal barterVehicleValue;
    private final Boolean barterProperty;
    private final BigDecimal barterPropertyValue;

    public Property(Integer id, Integer neighborhoodId, PropertyType type, Integer dorms, Integer suites, Integer bathrooms, Boolean pool, Boolean balcony, Boolean elevator, Boolean barbecueGrill, Integer garages, BigDecimal value, Boolean financing, BigDecimal financingValue, Boolean barterVehicle, BigDecimal barterVehicleValue, Boolean barterProperty, BigDecimal barterPropertyValue) {
        this.id = id;
        this.neighborhoodId = neighborhoodId;
        this.type = type;
        this.dorms = dorms;
        this.suites = suites;
        this.bathrooms = bathrooms;
        this.pool = pool;
        this.balcony = balcony;
        this.elevator = elevator;
        this.barbecueGrill = barbecueGrill;
        this.garages = garages;
        this.value = value;
        this.financing = financing;
        this.financingValue = financingValue;
        this.barterVehicle = barterVehicle;
        this.barterVehicleValue = barterVehicleValue;
        this.barterProperty = barterProperty;
        this.barterPropertyValue = barterPropertyValue;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNeighborhoodId() {
        return neighborhoodId;
    }

    public PropertyType getType() {
        return type;
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

    public Integer getGarages() {
        return garages;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Boolean getFinancing() {
        return financing;
    }

    public BigDecimal getFinancingValue() {
        return financingValue;
    }

    public Boolean getBarterVehicle() {
        return barterVehicle;
    }

    public BigDecimal getBarterVehicleValue() {
        return barterVehicleValue;
    }

    public Boolean getBarterProperty() {
        return barterProperty;
    }

    public BigDecimal getBarterPropertyValue() {
        return barterPropertyValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(id, property.id) &&
                Objects.equals(neighborhoodId, property.neighborhoodId) &&
                type == property.type &&
                Objects.equals(dorms, property.dorms) &&
                Objects.equals(suites, property.suites) &&
                Objects.equals(bathrooms, property.bathrooms) &&
                Objects.equals(pool, property.pool) &&
                Objects.equals(balcony, property.balcony) &&
                Objects.equals(elevator, property.elevator) &&
                Objects.equals(barbecueGrill, property.barbecueGrill) &&
                Objects.equals(garages, property.garages) &&
                Objects.equals(value, property.value) &&
                Objects.equals(financing, property.financing) &&
                Objects.equals(financingValue, property.financingValue) &&
                Objects.equals(barterVehicle, property.barterVehicle) &&
                Objects.equals(barterVehicleValue, property.barterVehicleValue) &&
                Objects.equals(barterProperty, property.barterProperty) &&
                Objects.equals(barterPropertyValue, property.barterPropertyValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, neighborhoodId, type, dorms, suites, bathrooms, pool, balcony, elevator, barbecueGrill, garages, value, financing, financingValue, barterVehicle, barterVehicleValue, barterProperty, barterPropertyValue);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Property.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("neighborhoodId=" + neighborhoodId)
                .add("type=" + type)
                .add("dorms=" + dorms)
                .add("suites=" + suites)
                .add("bathrooms=" + bathrooms)
                .add("pool=" + pool)
                .add("balcony=" + balcony)
                .add("elevator=" + elevator)
                .add("barbecueGrill=" + barbecueGrill)
                .add("garages=" + garages)
                .add("value=" + value)
                .add("financing=" + financing)
                .add("financingValue=" + financingValue)
                .add("barterVehicle=" + barterVehicle)
                .add("barterVehicleValue=" + barterVehicleValue)
                .add("barterProperty=" + barterProperty)
                .add("barterPropertyValue=" + barterPropertyValue)
                .toString();
    }
}


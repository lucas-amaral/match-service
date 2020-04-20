package com.proposta.aceita.matchservice.entities;

import com.proposta.aceita.matchservice.entities.enums.PropertyType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

@Document("sales")
public class Sale {
    @Id
    private final Integer id;
    private final Integer propertyId;
    private final Integer neighborhoodId;
    private final PropertyType type;
    private final Integer dorms;
    private final Integer suites;
    private final Integer bathrooms;
    private final boolean pool;
    private final boolean balcony;
    private final boolean elevator;
    private final boolean barbecueGrill;
    private final Integer garages;
    private final BigDecimal value;
    private final boolean financing;
    private final BigDecimal financingValue;
    private final boolean barterVehicle;
    private final BigDecimal barterVehicleValue;
    private final boolean barterProperty;
    private final BigDecimal barterPropertyValue;

    public Sale(Integer id, Integer propertyId, Integer neighborhoodId, PropertyType type, Integer dorms, Integer suites, Integer bathrooms, boolean pool, boolean balcony, boolean elevator, boolean barbecueGrill, Integer garages, BigDecimal value, boolean financing, BigDecimal financingValue, boolean barterVehicle, BigDecimal barterVehicleValue, boolean barterProperty, BigDecimal barterPropertyValue) {
        this.id = id;
        this.propertyId = propertyId;
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

    public Integer getPropertyId() {
        return propertyId;
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
        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id) &&
                Objects.equals(propertyId, sale.propertyId) &&
                Objects.equals(neighborhoodId, sale.neighborhoodId) &&
                type == sale.type &&
                Objects.equals(dorms, sale.dorms) &&
                Objects.equals(suites, sale.suites) &&
                Objects.equals(bathrooms, sale.bathrooms) &&
                Objects.equals(pool, sale.pool) &&
                Objects.equals(balcony, sale.balcony) &&
                Objects.equals(elevator, sale.elevator) &&
                Objects.equals(barbecueGrill, sale.barbecueGrill) &&
                Objects.equals(garages, sale.garages) &&
                Objects.equals(value, sale.value) &&
                Objects.equals(financing, sale.financing) &&
                Objects.equals(financingValue, sale.financingValue) &&
                Objects.equals(barterVehicle, sale.barterVehicle) &&
                Objects.equals(barterVehicleValue, sale.barterVehicleValue) &&
                Objects.equals(barterProperty, sale.barterProperty) &&
                Objects.equals(barterPropertyValue, sale.barterPropertyValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, propertyId, neighborhoodId, type, dorms, suites, bathrooms, pool, balcony, elevator, barbecueGrill, garages, value, financing, financingValue, barterVehicle, barterVehicleValue, barterProperty, barterPropertyValue);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Sale.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("propertyId=" + propertyId)
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


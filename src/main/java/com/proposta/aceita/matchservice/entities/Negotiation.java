package com.proposta.aceita.matchservice.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;


@Document("negotiations")
public class Negotiation {
    @MongoId(value = FieldType.OBJECT_ID)
    private String id;
    private final Interest interest;
    private final Sale sale;
    private final LocalDateTime createdAt;

    public Negotiation(String id, Interest interest, Sale sale, LocalDateTime createdAt) {
        this.id = id;
        this.interest = interest;
        this.sale = sale;
        this.createdAt = createdAt;
    }

    public static Negotiation of(Interest interest, Sale sale) {
        return new Negotiation(null, interest, sale, LocalDateTime.now());
    }

    public String getId() {
        return id;
    }

    public Interest getInterest() {
        return interest;
    }

    public Sale getSale() {
        return sale;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Negotiation that = (Negotiation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(interest, that.interest) &&
                Objects.equals(sale, that.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, interest, sale);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Negotiation.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("interest=" + interest)
                .add("sale=" + sale)
                .toString();
    }
}


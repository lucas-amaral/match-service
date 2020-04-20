package com.proposta.aceita.matchservice.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;


@Document("negotiations_approved_by_seller")
public class NegotiationApprovedBySeller {
    @MongoId
    private final String id;
    private final Interest interest;
    private final Sale sale;
    private final LocalDateTime createdAt;
    
    public NegotiationApprovedBySeller(String id, Interest interest, Sale sale, LocalDateTime createdAt) {
        this.id = id;
        this.interest = interest;
        this.sale = sale;
        this.createdAt = createdAt;
    }

    public static NegotiationApprovedBySeller of(Negotiation negotiation) {
        return new NegotiationApprovedBySeller(null, negotiation.getInterest(), negotiation.getSale(), LocalDateTime.now());
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
        NegotiationApprovedBySeller that = (NegotiationApprovedBySeller) o;
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
        return new StringJoiner(", ", NegotiationApprovedBySeller.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("interest=" + interest)
                .add("sale=" + sale)
                .toString();
    }
}


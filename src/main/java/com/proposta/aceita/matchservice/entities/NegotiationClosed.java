package com.proposta.aceita.matchservice.entities;

import com.proposta.aceita.matchservice.entities.enums.NegotiationStatus;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

import static com.proposta.aceita.matchservice.entities.enums.NegotiationStatus.NOT_APPROVED_BY_THE_SELLER;


@Document("negotiations_closed")
public class NegotiationClosed {
    @MongoId(value = FieldType.OBJECT_ID)
    private final String id;
    private final Interest interest;
    private final Sale sale;
    private final NegotiationStatus status;
    private final LocalDateTime createdAt;

    public NegotiationClosed(String id, Interest interest, Sale sale, NegotiationStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.interest = interest;
        this.sale = sale;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static NegotiationClosed of(Negotiation negotiation) {
        return new NegotiationClosed(negotiation.getId(), negotiation.getInterest(), negotiation.getSale(), NOT_APPROVED_BY_THE_SELLER, LocalDateTime.now());
    }

    public static NegotiationClosed of(NegotiationApprovedBySeller negotiation, NegotiationStatus status) {
        return new NegotiationClosed(negotiation.getId(), negotiation.getInterest(), negotiation.getSale(), status, LocalDateTime.now());
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

    public NegotiationStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NegotiationClosed that = (NegotiationClosed) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(interest, that.interest) &&
                Objects.equals(sale, that.sale) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, interest, sale, status);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NegotiationClosed.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("interest=" + interest)
                .add("sale=" + sale)
                .add("status=" + status)
                .toString();
    }
}


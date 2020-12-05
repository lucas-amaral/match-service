package com.proposta.aceita.matchservice.entities;

import com.proposta.aceita.matchservice.entities.enums.BarterType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.StringJoiner;

@Document("barters")
public class Barter {
    private final Integer id;
    private final BarterType type;
    private final Double value;

    public Barter(Integer id, BarterType type, Double value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public BarterType getType() {
        return type;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barter barter = (Barter) o;
        return Objects.equals(id, barter.id) &&
                type == barter.type &&
                Objects.equals(value, barter.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, value);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Barter.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("type=" + type)
                .add("value=" + value)
                .toString();
    }
}

package com.proposta.aceita.matchservice.entities.enums;

public enum BarterType {
    CAR("carro"), PROPERTY("imóvel");

    private String type;

    BarterType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

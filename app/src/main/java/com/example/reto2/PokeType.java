package com.example.reto2;

import java.io.Serializable;

public class PokeType implements Serializable {
    private Type type;

    public PokeType(Type type) {
        this.type = type;
    }

    public PokeType() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

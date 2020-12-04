package com.example.reto2;

import java.io.Serializable;

public class PokeSprites implements Serializable {
    private String front_default;

    public PokeSprites(String front_default) {
        this.front_default = front_default;
    }

    public PokeSprites() {
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }
}

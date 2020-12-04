package com.example.reto2;


import java.io.Serializable;

public class PokemonStats implements Serializable {
    private String base_stat;

    public PokemonStats(String base_stat) {
        this.base_stat = base_stat;
    }

    public PokemonStats() {
    }

    public String getBase_stat() {
        return base_stat;
    }

    public void setBase_stat(String base_stat) {
        this.base_stat = base_stat;
    }
}

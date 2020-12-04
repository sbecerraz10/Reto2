package com.example.reto2;

import java.io.Serializable;
import java.util.List;

public class Pokemon implements Serializable {
    private String name;
    private PokeSprites sprites;
    private List<PokemonStats> stats;
    private List<PokeType> types;
    private long timestamp;

    public Pokemon() {
    }

    public Pokemon(String name, PokeSprites sprites, List<PokemonStats> stats, List<PokeType> types) {
        this.name = name;
        this.sprites = sprites;
        this.stats = stats;
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public PokeSprites getSprites() {
        return sprites;
    }

    public List<PokemonStats> getStats() {
        return stats;
    }

    public List<PokeType> getTypes() {
        return types;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSprites(PokeSprites sprites) {
        this.sprites = sprites;
    }

    public void setStats(List<PokemonStats> stats) {
        this.stats = stats;
    }

    public void setTypes(List<PokeType> types) {
        this.types = types;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

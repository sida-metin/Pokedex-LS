package edu.url.salle.sida.metin.pokedexls;

import java.util.ArrayList;
import java.util.List;

public class User {

    private List<Pokemon> pokemons;
    private int money;

    public void updateMoney(int moneyEarned) {
        this.money += moneyEarned;
    }

    public List<Pokemon> getPokemons() {
        return this.pokemons;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    private List<Pokemon> capturedPokemons = new ArrayList<>();

    public List<Pokemon> getCapturedPokemons() {
        return capturedPokemons;
    }

    public boolean capturePokemon(Pokemon pokemon) {
        if (capturedPokemons.size() < 6 && !pokemon.isCaptured() && Math.random() <= pokemon.captureProbability()) {
            pokemon.setCaptured(true);
            capturedPokemons.add(pokemon);
            System.out.println("Pokemon captured!");
            return true;
        } else {
            System.out.println("Pokemon cannot be captured!");
            return false;
        }
    }

    public void releasePokemon(Pokemon pokemon) {
        if (pokemon.isCaptured()) {
            pokemon.setCaptured(false);
            capturedPokemons.remove(pokemon);
            System.out.println("Pokemon released!");
        } else {
            System.out.println("Pokemon is not captured!");
        }
    }
}
package edu.url.salle.sida.metin.pokedexls;

public class PokemonResponse {
    private int count;
    private String next;
    private String previous;
    private Pokemon[] results;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public Pokemon[] getResults() {
        return results;
    }
}

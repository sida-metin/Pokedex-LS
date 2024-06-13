package edu.url.salle.sida.metin.pokedexls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pokemon implements Serializable {

    private String frontImageUrl;
    private String backImageUrl;
    private String name;
    private int id;
    private String url;
    private String type;
    private String imageUrl;
    private List<String> abilitiesUrl;
    private List<String> typesUrl;
    private List<String> abilities;
    private List<String> types;
    private List<String> stats;

    private String shinyImageUrl;

    private boolean captured = false;
    private String pokeballType;



    public boolean isCaptured() {
        return captured;
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }


    public String getShinyImageUrl() {
        return shinyImageUrl;
    }

    public void setShinyImageUrl(String shinyImageUrl) {
        this.shinyImageUrl = shinyImageUrl;
    }


    public String getFrontImageUrl() {
        return frontImageUrl;
    }

    public void setFrontImageUrl(String frontImageUrl) {
        this.frontImageUrl = frontImageUrl;
    }

    public String getBackImageUrl() {
        return backImageUrl;
    }

    public void setBackImageUrl(String backImageUrl) {
        this.backImageUrl = backImageUrl;
    }

    public String getPokeballType() {
        return pokeballType;
    }

    public void setPokeballType(String pokeballType) {
        this.pokeballType = pokeballType;
    }

    public double captureProbability() {
        double base = (600 - this.getType().length()) / 600.0;
        switch (this.pokeballType) {
            case "Pokeball":
                return base;
            case "Superball":
                return base * 1.5;
            case "Ultraball":
                return base * 2;
            case "Masterball":
                return 1;
            default:
                return 0;
        }
    }






    public Pokemon() {
        abilitiesUrl = new ArrayList<>();
        abilities = new ArrayList<>();
        types = new ArrayList<>();
    }

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStats() {
        return stats;
    }

    public void setStats(List<String> stats) {
        this.stats = stats;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getAbilitiesUrl() {
        return abilitiesUrl;
    }

    public void setAbilitiesUrl(List<String> abilitiesUrl) {
        this.abilitiesUrl = abilitiesUrl;
    }

    public List<String> getTypesUrl() {
        return typesUrl;
    }

    public void setTypesUrl(List<String> typesUrl) {
        this.typesUrl = typesUrl;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
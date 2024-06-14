package edu.url.salle.sida.metin.pokedexls;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class User {
    private static User instance = null;
    private List<Pokemon> pokemons;
    private int money;
    private Context context;
    private Set<String> inventory;



    public void updateMoney(int moneyEarned) {
        this.money += moneyEarned;
    }

    public interface OnMoneyChangeListener {
    void onMoneyChange(int newMoney);
    }


    private OnMoneyChangeListener onMoneyChangeListener;

    public void setOnMoneyChangeListener(OnMoneyChangeListener onMoneyChangeListener) {
        this.onMoneyChangeListener = onMoneyChangeListener;
    }

    public User(Context context) {
        this.context = context;
        SharedPreferences sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        this.inventory = new HashSet<>(sharedPref.getStringSet("inventory", new HashSet<>()));
        this.money = sharedPref.getInt("money", 0);
    }

    public Set<String> getInventory() {
        return this.inventory;
    }

    public void buyItem(String item, int price) {
        this.inventory.add(item);
        SharedPreferences sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet("inventory", this.inventory);
        editor.apply();
    }

    public static User getInstance(Context context) {
        if (instance == null) {
            instance = new User(context);
        }
        return instance;
    }

    public List<Pokemon> getPokemons() {
        return this.pokemons;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;

        SharedPreferences sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("money", money);
        editor.apply();
        if (onMoneyChangeListener != null) {
            onMoneyChangeListener.onMoneyChange(money);
        }
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
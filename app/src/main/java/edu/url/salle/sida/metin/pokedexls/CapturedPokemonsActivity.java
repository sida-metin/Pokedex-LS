package edu.url.salle.sida.metin.pokedexls;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

public class CapturedPokemonsActivity extends AppCompatActivity {

    private ListView capturedPokemonsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captured_pokemons);


        capturedPokemonsListView = findViewById(R.id.captured_pokemons_list);

        // Get the captured Pokemon details from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("CapturedPokemons", MODE_PRIVATE);
        Map<String, ?> capturedPokemons = sharedPreferences.getAll();

        // Convert the captured Pokemon details to a list
        ArrayList<String> capturedPokemonsList = new ArrayList<>();
        for (Object value : capturedPokemons.values()) {
            capturedPokemonsList.add(String.valueOf(value));
        }

        // Display the captured Pokemon details in the list view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, capturedPokemonsList);
        capturedPokemonsListView.setAdapter(adapter);
    }
}
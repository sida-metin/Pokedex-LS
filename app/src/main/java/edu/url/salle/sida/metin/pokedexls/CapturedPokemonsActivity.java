package edu.url.salle.sida.metin.pokedexls;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CapturedPokemonsActivity extends AppCompatActivity {
    private ListView capturedPokemonsList;
    private PokeTeamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captured_pokemons);

        capturedPokemonsList = findViewById(R.id.captured_pokemons_list);

        // Initialize the adapter
        adapter = new PokeTeamAdapter(this, R.layout.list_item_pokemon, getPokemons());

        // Set the adapter to the ListView
        capturedPokemonsList.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<String> getPokemons() {
        SharedPreferences sharedPreferences = getSharedPreferences("CapturedPokemons", MODE_PRIVATE);
        Map<String, ?> capturedPokemons = sharedPreferences.getAll();

        ArrayList<String> capturedPokemonsList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : capturedPokemons.entrySet()) {
            capturedPokemonsList.add(entry.getKey());
        }

        return capturedPokemonsList;
    }
}
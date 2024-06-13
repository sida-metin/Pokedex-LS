package edu.url.salle.sida.metin.pokedexls;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        capturedPokemonsListView = findViewById(R.id.captured_pokemons_list);

        SharedPreferences sharedPreferences = getSharedPreferences("CapturedPokemons", MODE_PRIVATE);
        Map<String, ?> capturedPokemons = sharedPreferences.getAll();

        ArrayList<String> capturedPokemonsList = new ArrayList<>();
        for (Object value : capturedPokemons.values()) {
            capturedPokemonsList.add(String.valueOf(value));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, capturedPokemonsList);
        capturedPokemonsListView.setAdapter(adapter);
    }
}
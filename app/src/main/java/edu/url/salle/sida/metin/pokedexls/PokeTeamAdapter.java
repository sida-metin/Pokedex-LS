package edu.url.salle.sida.metin.pokedexls;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class PokeTeamAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;

    public PokeTeamAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_pokemon, parent, false);
        }

        TextView pokemonName = convertView.findViewById(R.id.pokemon_name);
        pokemonName.setText(getItem(position));

        // In your PokeTeamAdapter
        Button releaseButton = convertView.findViewById(R.id.release_button);
        releaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the Pokemon name
                String pokemonName = getItem(position);

                // Remove the Pokemon from SharedPreferences
                deselectPokemon(pokemonName);

                // Remove the Pokemon from the list and update the ListView
                remove(pokemonName);
                notifyDataSetChanged();
            }

            private void deselectPokemon(String pokemonName) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("CapturedPokemons", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(pokemonName);
                editor.apply();
            }
            
        });

        return convertView;
    }
}
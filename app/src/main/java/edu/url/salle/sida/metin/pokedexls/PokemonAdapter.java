package edu.url.salle.sida.metin.pokedexls;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> implements Filterable {
    private List<Pokemon> pokemonList;
    private List<Pokemon> pokemonListFull;
    private Context context;
    private SharedPreferences sharedPreferences;

    public PokemonAdapter(List<Pokemon> pokemonList, Context context) {
        this.pokemonList = pokemonList;
        this.context = context;
        this.pokemonListFull = new ArrayList<>(pokemonList);
        this.sharedPreferences = context.getSharedPreferences("CapturedPokemons", Context.MODE_PRIVATE);
    }

    public void addPokemon(Pokemon pokemon) {
        Random random = new Random();
        int shinyChance = random.nextInt(500);

        if (shinyChance == 0) {
            pokemon.setShinyImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/" + pokemon.getId() + ".png");
        }

        this.pokemonList.add(pokemon);
        this.pokemonListFull.add(pokemon);
        notifyDataSetChanged();
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_list_item, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        final Pokemon pokemon = pokemonList.get(position);
        holder.bind(pokemon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PokemonDetailActivity.class);
                intent.putExtra("pokemon", pokemon);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList != null ? pokemonList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return pokemonFilter;
    }

    private Filter pokemonFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Pokemon> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(pokemonListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Pokemon item : pokemonListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pokemonList.clear();
            if (results.values != null) {
                pokemonList.addAll((List) results.values);
            }
            notifyDataSetChanged();
        }
    };

    class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView pokemonNameView;
        ImageView pokemonImageView;
        TextView pokemonTypeView;
        ImageView pokemonCaughtView;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            pokemonNameView = itemView.findViewById(R.id.pokemon_name);
            pokemonImageView = itemView.findViewById(R.id.pokemon_image);
            pokemonTypeView = itemView.findViewById(R.id.pokemon_type);
            pokemonCaughtView = itemView.findViewById(R.id.pokemon_caught);
        }

        public void bind(Pokemon pokemon) {
            pokemonNameView.setText(pokemon.getName());
            pokemonTypeView.setText(pokemon.getType());

            if (pokemon.getShinyImageUrl() != null) {
                Glide.with(itemView)
                        .load(pokemon.getShinyImageUrl())
                        .into(pokemonImageView);
            } else {
                Glide.with(itemView)
                        .load(pokemon.getUrl())
                        .into(pokemonImageView);
            }
            SharedPreferences sharedPreferences = itemView.getContext().getSharedPreferences("CapturedPokemons", Context.MODE_PRIVATE);

            if (sharedPreferences.getBoolean(pokemon.getName(), false)) {
                pokemonCaughtView.setVisibility(View.VISIBLE);
            } else {
                pokemonCaughtView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Pokemon clickedPokemon = pokemonList.get(position);

                Intent intent = new Intent(view.getContext(), PokemonDetailActivity.class);
                intent.putExtra("pokemon", clickedPokemon);
                view.getContext().startActivity(intent);
            }
        }
    }
}
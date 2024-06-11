package edu.url.salle.sida.metin.pokedexls;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> implements Filterable {
    private List<Pokemon> pokemonList;
    private List<Pokemon> pokemonListFull;

    private Context context;


    public PokemonAdapter(List<Pokemon> pokemonList, Context context) {
        this.pokemonList = pokemonList;
        this.context = context;
        pokemonListFull = new ArrayList<>(pokemonList);
    }

    public void addPokemon(Pokemon pokemon) {
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

    public PokemonViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        pokemonNameView = itemView.findViewById(R.id.pokemon_name);
        pokemonImageView = itemView.findViewById(R.id.pokemon_image);
        pokemonTypeView = itemView.findViewById(R.id.pokemon_type);
    }

    public void bind(Pokemon pokemon) {
        pokemonNameView.setText(pokemon.getName());
        pokemonTypeView.setText(pokemon.getType());
        Glide.with(itemView)
                .load(pokemon.getUrl())
                .into(pokemonImageView);
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
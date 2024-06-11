package edu.url.salle.sida.metin.pokedexls;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import com.bumptech.glide.Glide;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Pokemon> pokemons;

    public MyAdapter(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemondetail, parent, false);
        return new MyViewHolder(view, pokemons);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.textView.setText(pokemon.getName());
        Glide.with(holder.imageView.getContext()).load(pokemon.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView textView;
        private List<Pokemon> pokemons;

        public MyViewHolder(@NonNull View itemView, List<Pokemon> pokemons) {
            super(itemView);
            this.pokemons = pokemons;
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.pokemon_image);
            textView = itemView.findViewById(R.id.pokemon_name);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Pokemon clickedPokemon = pokemons.get(position);

            Intent intent = new Intent(view.getContext(), PokemonDetailActivity.class);
            intent.putExtra("pokemon", clickedPokemon);
            view.getContext().startActivity(intent);
        }
    }
}
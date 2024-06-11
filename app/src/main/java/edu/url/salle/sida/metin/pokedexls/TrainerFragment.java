package edu.url.salle.sida.metin.pokedexls;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TrainerFragment extends Fragment {

    private Button itemsButton;
    private Button capturedPokemonsButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer, container, false);

        itemsButton = view.findViewById(R.id.items);
        capturedPokemonsButton = view.findViewById(R.id.captured_pokemons);

        itemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ItemsActivity.class);
                startActivity(intent);
            }
        });

        capturedPokemonsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CapturedPokemonsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
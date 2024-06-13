package edu.url.salle.sida.metin.pokedexls;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TrainerFragment extends Fragment {

    private EditText trainerNameEditText;
    private TextView trainerNameDisplayTextView;

    private TextView moneyTextView;
    private Button itemsButton;
    private Button capturedPokemonsButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer, container, false);

        trainerNameEditText = view.findViewById(R.id.trainer_name);
        moneyTextView = view.findViewById(R.id.money);
        itemsButton = view.findViewById(R.id.items);
        capturedPokemonsButton = view.findViewById(R.id.captured_pokemons);

        trainerNameDisplayTextView = view.findViewById(R.id.trainer_name_display);

        trainerNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                trainerNameDisplayTextView.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
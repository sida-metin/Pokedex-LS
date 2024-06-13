package edu.url.salle.sida.metin.pokedexls;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class PokemonDetailActivity extends Activity {

    private User user;
    private Pokemon pokemon;
    private Button pokeballButton;
    private Button superballButton;
    private Button ultraballButton;
    private Button masterballButton;


    private void resetButtonColors() {
        pokeballButton.setBackgroundColor(Color.GRAY);
        superballButton.setBackgroundColor(Color.GRAY);
        ultraballButton.setBackgroundColor(Color.GRAY);
        masterballButton.setBackgroundColor(Color.GRAY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemondetail);

        Intent intent = getIntent();
        Pokemon pokemon = (Pokemon) intent.getSerializableExtra("pokemon");

        user = new User();


        Button captureButton = findViewById(R.id.capture_button);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pokemonName = pokemon.getName();
                SharedPreferences sharedPreferences = getSharedPreferences("CapturedPokemons", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (sharedPreferences.getBoolean(pokemonName, false)) {
                    editor.remove(pokemonName);
                    editor.apply();
                    Toast.makeText(PokemonDetailActivity.this, "Pokemon released!", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, ?> allEntries = sharedPreferences.getAll();
                    if (allEntries.size() >= 6) {
                        Toast.makeText(PokemonDetailActivity.this, "You have already captured 6 Pokemons. Release one before capturing another.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    editor.putBoolean(pokemonName, true);
                    editor.apply();
                    Toast.makeText(PokemonDetailActivity.this, "Pokemon captured!", Toast.LENGTH_SHORT).show();

                    int moneyEarned = pokemon.calculateMoney();

                    user.setMoney(user.getMoney() + moneyEarned);

                    TextView moneyTextView = findViewById(R.id.money_text_view);
                    moneyTextView.setText("Money: " + user.getMoney());
                }
            }
        });




        pokeballButton = findViewById(R.id.pokeball_button);
        pokeballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokemon.setPokeballType("Pokeball");
                boolean isCaptured = user.capturePokemon(pokemon);
                resetButtonColors();
                pokeballButton.setBackgroundColor(Color.MAGENTA);

                SharedPreferences sharedPreferences = getSharedPreferences("SelectedItems", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedBall", "Pokeball");
                editor.apply();
                Toast.makeText(PokemonDetailActivity.this, "Item selected!", Toast.LENGTH_SHORT).show();
            }
        });

        superballButton = findViewById(R.id.superball_button);
        superballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokemon.setPokeballType("Superball");
                user.capturePokemon(pokemon);
                resetButtonColors();
                superballButton.setBackgroundColor(Color.MAGENTA);
                SharedPreferences sharedPreferences = getSharedPreferences("SelectedItems", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedItem", "Superball");
                editor.apply();
                Toast.makeText(PokemonDetailActivity.this, "Item selected!", Toast.LENGTH_SHORT).show();
            }
        });

        ultraballButton = findViewById(R.id.ultraball_button);
        ultraballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokemon.setPokeballType("Ultraball");
                user.capturePokemon(pokemon);
                resetButtonColors();
                ultraballButton.setBackgroundColor(Color.MAGENTA);
                SharedPreferences sharedPreferences = getSharedPreferences("SelectedItems", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedItem", "Ultraball");
                editor.apply();
                Toast.makeText(PokemonDetailActivity.this, "Item selected!", Toast.LENGTH_SHORT).show();
            }
        });

        masterballButton = findViewById(R.id.masterball_button);
        masterballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokemon.setPokeballType("Masterball");
                user.capturePokemon(pokemon);
                resetButtonColors();
                masterballButton.setBackgroundColor(Color.MAGENTA);
                SharedPreferences sharedPreferences = getSharedPreferences("SelectedItems", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedItem", "Masterball");
                editor.apply();
                Toast.makeText(PokemonDetailActivity.this, "Item selected!", Toast.LENGTH_SHORT).show();
            }
        });






        try {

            ImageView imageView = findViewById(R.id.pokemon_image);
            TextView nameTextView = findViewById(R.id.pokemon_name);
            nameTextView.setText(pokemon.getName());
            LinearLayout abilityLayout = findViewById(R.id.pokemon_ability);
            TextView typeTextView = findViewById(R.id.pokemon_type);
            ImageButton backButton = findViewById(R.id.back_button);
            LinearLayout statsLayout = findViewById(R.id.stats_layout);

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            Glide.with(this).load(pokemon.getUrl()).into(imageView);

            String pokemonNameUrl = "https://pokeapi.co/api/v2/pokemon/" + pokemon.getId();
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest pokemonNameRequest = new JsonObjectRequest
                (Request.Method.GET, pokemonNameUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("PokemonDetailActivity", "API Response: " + response.toString());
                            String pokemonName = response.getString("name");
                            nameTextView.setText(pokemonName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("PokemonDetailActivity", "Error in network request", error);
                        error.printStackTrace();
                    }
                });

            queue.add(pokemonNameRequest);

            List<String> abilityUrls = pokemon.getAbilitiesUrl();
            for (String abilityUrl : abilityUrls) {
                JsonObjectRequest abilityDetailRequest = new JsonObjectRequest
                    (Request.Method.GET, abilityUrl, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String abilityName = response.getString("name");

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(30, 0, 30, 0);

                                Button abilityButton = new Button(PokemonDetailActivity.this);
                                abilityButton.setText(abilityName);
                                abilityButton.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                                abilityButton.setTextColor(Color.BLACK);
                                abilityButton.setLayoutParams(params);
                                abilityButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        for (int i = 0; i < abilityLayout.getChildCount(); i++) {
                                            View child = abilityLayout.getChildAt(i);

                                            if (child instanceof Button) {
                                                if (child == v) {
                                                    child.setBackgroundTintList(ColorStateList.valueOf(Color.MAGENTA));
                                                } else {
                                                    child.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                                                }
                                                child.setEnabled(true);
                                            }
                                        }
                                    }
                                });
                                abilityLayout.addView(abilityButton);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("PokemonDetailActivity", "Error in network request", error);
                            error.printStackTrace();
                        }
                    });

                queue.add(abilityDetailRequest);
            }


            String pokemonTypesUrl = "https://pokeapi.co/api/v2/pokemon/" + pokemon.getId();
            JsonObjectRequest pokemonTypesRequest = new JsonObjectRequest
                    (Request.Method.GET, pokemonTypesUrl, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray types = response.getJSONArray("types");
                                TextView typeTextView = findViewById(R.id.pokemon_type);

                                for (int i = 0; i < types.length(); i++) {
                                    JSONObject type = types.getJSONObject(i);
                                    String typeName = type.getJSONObject("type").getString("name");
                                    typeTextView.append(typeName + "\n");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("PokemonDetailActivity", "Error in network request", error);
                            error.printStackTrace();
                        }
                    });

            queue.add(pokemonTypesRequest);



            String pokemonStatsUrl = "https://pokeapi.co/api/v2/pokemon/" + pokemon.getId();
            JsonObjectRequest pokemonStatsRequest = new JsonObjectRequest
                    (Request.Method.GET, pokemonStatsUrl, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray stats = response.getJSONArray("stats");

                                for (int i = 0; i < stats.length(); i++) {
                                    JSONObject stat = stats.getJSONObject(i);
                                    String statName = stat.getJSONObject("stat").getString("name");
                                    int baseStat = stat.getInt("base_stat");

                                    statName = statName.substring(0, 1).toUpperCase() + statName.substring(1);

                                    TextView statNameTextView = new TextView(PokemonDetailActivity.this);
                                    statNameTextView.setText(statName + ":");
                                    statNameTextView.setTypeface(null, Typeface.BOLD);
                                    statsLayout.addView(statNameTextView);

                                    ProgressBar statProgressBar = new ProgressBar(PokemonDetailActivity.this, null, android.R.attr.progressBarStyleHorizontal);
                                    statProgressBar.setMax(100);
                                    statProgressBar.setProgress(baseStat);
                                    statProgressBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                                    statsLayout.addView(statProgressBar);


                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("PokemonDetailActivity", "Error in network request", error);
                            error.printStackTrace();
                        }
                    });

            queue.add(pokemonStatsRequest);



            String pokemonDescriptionUrl = "https://pokeapi.co/api/v2/pokemon-species/" + pokemon.getId();
            JsonObjectRequest pokemonDescriptionRequest = new JsonObjectRequest
                (Request.Method.GET, pokemonDescriptionUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray flavorTextEntries = response.getJSONArray("flavor_text_entries");
                            for (int i = 0; i < flavorTextEntries.length(); i++) {
                                JSONObject flavorTextEntry = flavorTextEntries.getJSONObject(i);
                                if (flavorTextEntry.getJSONObject("language").getString("name").equals("en")) {
                                    String pokemonDescription = flavorTextEntry.getString("flavor_text");
                                    pokemonDescription = pokemonDescription.replace("\n", " ");
                                    TextView descriptionTextView = findViewById(R.id.pokemon_description);
                                    descriptionTextView.setText(pokemonDescription);
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("PokemonDetailActivity", "Error in network request", error);
                        error.printStackTrace();
                    }
                });

            queue.add(pokemonDescriptionRequest);

        } catch (Exception e) {
            Log.e("PokemonDetailActivity", "Error in onCreate", e);
        }
    }

}
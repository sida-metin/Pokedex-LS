package edu.url.salle.sida.metin.pokedexls;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.widget.SearchView;

public class PokedexFragment extends Fragment {
    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;
    private SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pokedex, container, false);

        searchView = view.findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pokemonAdapter.getFilter().filter(newText);
                return false;
            }
        });
        

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchView.setIconified(false);
                } else {
                    searchView.setIconified(true);
                }
            }
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        pokemonAdapter = new PokemonAdapter(new ArrayList<>(), getActivity());
        recyclerView.setAdapter(pokemonAdapter);

        Button pokeServiceButton = view.findViewById(R.id.my_button);
        pokeServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabBarActivity.class);
                startActivity(intent);
            }
        });

        TextView textView = view.findViewById(R.id.text_view);
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.pokeball);
        int h = drawable.getIntrinsicHeight();
        int w = drawable.getIntrinsicWidth();
        drawable.setBounds(0, 0, w / 18, h / 18);
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setCompoundDrawablePadding((int) (16 * getResources().getDisplayMetrics().density));

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://pokeapi.co/api/v2/pokemon";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject pokemonObject = results.getJSONObject(i);
                        String pokemonDetailUrl = pokemonObject.getString("url");

                        JsonObjectRequest pokemonDetailRequest = new JsonObjectRequest(Request.Method.GET, pokemonDetailUrl, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Pokemon pokemon = new Pokemon();
                                    pokemon.setName(response.getString("name"));
                                    pokemon.setId(response.getInt("id"));
                                    pokemon.setUrl(response.getJSONObject("sprites").getString("front_default"));

                                    pokemon.setShinyImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/" + pokemon.getId() + ".png");


                                    JSONArray abilities = response.getJSONArray("abilities");
                                    List<String> abilityUrls = new ArrayList<>();
                                    for (int j = 0; j < abilities.length(); j++) {
                                        JSONObject ability = abilities.getJSONObject(j);
                                        String abilityUrl = ability.getJSONObject("ability").getString("url");
                                        abilityUrls.add(abilityUrl);
                                    }
                                    pokemon.setAbilitiesUrl(abilityUrls);

                                    JSONArray types = response.getJSONArray("types");
                                    if (types.length() > 0) {
                                        JSONObject type = types.getJSONObject(0);
                                        String typeName = type.getJSONObject("type").getString("name");
                                        pokemon.setType(typeName);
                                    }

                                    pokemonAdapter.addPokemon(pokemon);
                                    pokemonAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("PokedexFragment", "Error in network request", error);
                                error.printStackTrace();
                            }
                        });

                        queue.add(pokemonDetailRequest);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PokedexFragment", "Error in network request", error);
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);

        return view;
    }
}